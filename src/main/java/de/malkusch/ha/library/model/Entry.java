package de.malkusch.ha.library.model;

import java.time.LocalDate;

public record Entry(MediumId medium, String title, LocalDate date) {

}
