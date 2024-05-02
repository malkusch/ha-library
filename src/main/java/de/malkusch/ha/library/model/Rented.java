package de.malkusch.ha.library.model;

import java.time.LocalDate;

import de.malkusch.ha.library.infrastructure.event.Event;

public record Rented(MediumId id, String title, LocalDate until) implements Event {
}
