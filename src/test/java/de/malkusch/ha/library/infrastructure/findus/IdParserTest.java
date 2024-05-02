package de.malkusch.ha.library.infrastructure.findus;

import static de.malkusch.ha.library.infrastructure.findus.IdParser.parseId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.malkusch.ha.library.model.MediumId;

public class IdParserTest {

    @ParameterizedTest
    @ValueSource(strings = { //
            "javascript:zeige_details('hilpoltstein_0033369001')", //
            "JavaScript:zeige_details('hilpoltstein_0033369001')", //
            "javascript:zeige_details(\"hilpoltstein_0033369001\")", //
    })
    public void parseIdShouldParse(String href) {
        var id = parseId(href);
        assertEquals(new MediumId("hilpoltstein_0033369001"), id);
    }

    @ParameterizedTest
    @ValueSource(strings = { //
            "", //
            "javascript:zeige_details('')", //
            "javascript:zeige_details(' ')", //
            "javascript:zeige_details()", //
            "javascript:invalid('hilpoltstein_0033369001')", //
    })
    public void parseIdShouldFail(String href) throws Exception {
        assertThrows(IllegalArgumentException.class, () -> parseId(href));
    }
}
