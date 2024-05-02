package de.malkusch.ha.library.model;

import de.malkusch.ha.library.infrastructure.event.Event;

public record Returned(MediumId medium) implements Event {

}
