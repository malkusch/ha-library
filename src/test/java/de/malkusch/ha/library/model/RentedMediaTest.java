package de.malkusch.ha.library.model;

import static java.util.Arrays.asList;
import static org.apache.commons.collections4.CollectionUtils.containsAny;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.paukov.combinatorics3.Generator;

public class RentedMediaTest {

    private static final MediumId[] IDS = asList("a", "b", "c", "d", "e") //
            .stream().map(MediumId::new).toArray(MediumId[]::new);

    static Stream<Scenario> allDiffScenarios() {
        // added: all
        // changed: all - added
        // removed: all - added - changed

        var diffs = DiffScenario.withAdded(Ids.all()) //
                .flatMap(it -> it.withChanged(Ids.all().exclude(it.added))) //
                .flatMap(it -> it.withRemoved(Ids.all().exclude(it.added).exclude(it.changed))) //
        ;

        var x = Ids.all().ids().toList();

        // init: all - added + changed + removed
        // sync: init + added + changed - removed

        var scenarios = diffs //
                .map(it -> new Scenario(null, null, it)) //

                .flatMap(it -> it.withInit(
                        Ids.all().exclude(it.expected.added).include(it.expected.changed).include(it.expected.removed))) //

                .map(it -> {
                    var sync = new ArrayList<>(it.init);
                    sync.addAll(it.expected.added);

                    sync.removeAll(it.expected.changed);
                    sync.addAll(changed(it.expected.changed));

                    sync.removeAll(it.expected.removed);
                    return it.withSync(sync);
                }) //
        ;

        return scenarios;
    }

    @ParameterizedTest
    @MethodSource
    public void allDiffScenarios(Scenario scenario) {
        var old = rentedMedia(scenario.init);
        var sync = rentedMedia(scenario.sync);
        var expected = diff(scenario.expected);

        var diff = old.diff(sync);

        assertEquals(expected, diff);
    }

    private static RentedMedia.Diff diff(DiffScenario diff) {
        var added = rentedMedia(diff.added);
        var changed = rentedMedia(changed(diff.changed));
        var removed = rentedMedia(diff.removed);

        return new RentedMedia.Diff(added.media(), changed.media(), removed.media());
    }

    private static RentedMedia rentedMedia(List<MediumId> ids) {
        return new RentedMedia(ids.stream() //
                .map(RentedMediaTest::rentedMedium) //
                .toList());
    }

    private record Scenario(List<MediumId> init, List<MediumId> sync, DiffScenario expected) {

        public Stream<Scenario> withInit(Ids init) {
            return init.ids().map(it -> new Scenario(it, sync, expected));
        }

        public Scenario withSync(List<MediumId> sync) {
            return new Scenario(init, sync, expected);
        }
    }

    private static record Ids(Stream<List<MediumId>> ids) {

        private static Ids all() {
            return new Ids(Generator.subset(IDS).simple().stream());
        }

        public Ids include(List<MediumId> list) {
            return new Ids(ids.filter(it -> it.containsAll(list)));
        }

        public Ids exclude(List<MediumId> list) {
            return new Ids(ids.filter(it -> !containsAny(it, list)));
        }
    }

    static record DiffScenario(List<MediumId> added, List<MediumId> changed, List<MediumId> removed) {

        public static Stream<DiffScenario> withAdded(Ids added) {
            return added.ids().map(it -> new DiffScenario(it, null, null));
        }

        public Stream<DiffScenario> withChanged(Ids changed) {
            return changed.ids().map(it -> new DiffScenario(added, it, removed));
        }

        public Stream<DiffScenario> withRemoved(Ids removed) {
            return removed.ids().map(it -> new DiffScenario(added, changed, it));
        }
    }

    private static final String ANY_TITLE = "any title";
    private static final LocalDate ANY_UNTIL = LocalDate.parse("2024-04-30");
    private static final LocalDate OTHER_UNTIL = LocalDate.parse("2024-05-03");

    private static RentedMedium rentedMedium(MediumId id) {
        var until = ANY_UNTIL;
        if (id.value().endsWith("2")) {
            until = OTHER_UNTIL;
            id = new MediumId(id.value().substring(0, 1));
        }
        return new RentedMedium(new Medium(id, ANY_TITLE), until);
    }

    private static MediumId changed(MediumId id) {
        return new MediumId(id.value() + "2");
    }

    private static List<MediumId> changed(List<MediumId> ids) {
        return ids.stream().map(RentedMediaTest::changed).toList();
    }
}
