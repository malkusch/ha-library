package de.malkusch.ha.library.model;

public record MediumId(String value) {

    @Override
    public final String toString() {
        return value;
    }
}
