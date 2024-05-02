package de.malkusch.ha.library.model;

public record Medium(MediumId id, String title) {

    @Override
    public final int hashCode() {
        return id.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        if (!(obj instanceof Medium other)) {
            return false;
        }
        return id.equals(other.id);
    }
}
