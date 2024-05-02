package de.malkusch.ha.library.model;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record RentedMedia(List<RentedMedium> media) {

    public final static RentedMedia EMPTY = new RentedMedia(emptyList());

    public record Diff(List<RentedMedium> added, List<RentedMedium> changed, List<RentedMedium> removed) {
    }

    public Diff diff(RentedMedia otherMedia) {
        List<RentedMedium> added = new ArrayList<>();
        List<RentedMedium> changed = new ArrayList<>();
        List<RentedMedium> removed = new ArrayList<>(media);

        for (var other : otherMedia.media) {
            var optionalOld = find(other.medium());
            if (optionalOld.isEmpty()) {
                added.add(other);

            } else {
                var old = optionalOld.get();
                remove(removed, old);
                if (old.isChanged(other)) {
                    changed.add(other);
                }
            }
        }

        return new Diff(added, changed, removed);
    }

    private void remove(List<RentedMedium> media, RentedMedium remove) {
        media.removeIf(it -> it.medium().equals(remove.medium()));
    }

    private Optional<RentedMedium> find(Medium medium) {
        return media.stream().filter(it -> it.medium().equals(medium)).findFirst();
    }
}
