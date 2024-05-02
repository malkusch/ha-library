package de.malkusch.ha.library.model;

import java.time.LocalDate;

public record RentedMedium(Medium medium, LocalDate until) {

    boolean isChanged(RentedMedium other) {
        return !equals(other);
    }

}
