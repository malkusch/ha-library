package de.malkusch.ha.library.model;

import static java.util.Objects.requireNonNull;

public record MediumId(String value) {

    public MediumId {
        requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException("Id must not be blank");
        }
    }

    @Override
    public final String toString() {
        return value;
    }
}
