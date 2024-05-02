package de.malkusch.ha.library.model;

import java.time.LocalDate;

import de.malkusch.ha.library.infrastructure.event.Event;

public record Extended(MediumId medium, LocalDate extension) implements Event {

}
