package de.malkusch.ha.library.infrastructure.findus;

import static java.util.Objects.requireNonNull;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.util.regex.Pattern;

import de.malkusch.ha.library.model.MediumId;

final class IdParser {

    private static final Pattern ID = compile( //
            "javascript:\\s*zeige_details\\s*\\(\\s*['\"]?([^\\s'\"]+)['\"]?\\s*\\)", //
            CASE_INSENSITIVE);

    static MediumId parseId(String href) {
        requireNonNull(href);
        var matcher = ID.matcher(href);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Could not parse Medium id in '" + href + "'");
        }
        var id = matcher.group(1);
        return new MediumId(id);
    }
}
