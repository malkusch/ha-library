package de.malkusch.ha.library.infrastructure.calendar;

import de.malkusch.ha.library.model.Calendar;
import de.malkusch.ha.library.model.Entry;
import de.malkusch.ha.library.model.MediumId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
final class NullCalendar implements Calendar {

    @Override
    public void add(Entry entry) {
        log.warn("Added {} to NullCalendar", entry);
    }

    @Override
    public void delete(MediumId id) {
        log.warn("Deleting {} from NullCalendar", id);
    }
}
