package de.malkusch.ha.library.infrastructure.calendar;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Event.ExtendedProperties;
import com.google.api.services.calendar.model.EventDateTime;

import de.malkusch.ha.library.model.Calendar;
import de.malkusch.ha.library.model.Entry;
import de.malkusch.ha.library.model.MediumId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
final class GoogleCalendar implements Calendar {

    private final com.google.api.services.calendar.Calendar api;
    private final String calendarId;
    private final String location;

    @Override
    public void add(Entry entry) throws IOException {
        var event = new Event();
        event.setSummary(entry.title());
        event.setLocation(location);

        var start = eventDate(entry.date());
        event.setStart(start);

        var end = eventDate(entry.date().plusDays(1));
        event.setEnd(end);

        var properties = new ExtendedProperties();
        properties.setPrivate(Map.of("medium", entry.medium().toString()));
        event.setExtendedProperties(properties);

        api.events().insert(calendarId, event).execute();
    }

    @Override
    public void deleteUpcoming(MediumId id) throws IOException {
        var list = api.events().list(calendarId);
        list.setPrivateExtendedProperty(Arrays.asList("medium=" + id.toString()));
        list.setTimeMin(new DateTime(new Date()));
        var events = list.execute().getItems();
        for (var event : events) {
            log.debug("Deleting {}", event.getId());
            api.events().delete(calendarId, event.getId()).execute();
        }
    }

    private static EventDateTime eventDate(LocalDate date) {
        var time = new EventDateTime();
        time.setDate(new DateTime(date.format(ISO_LOCAL_DATE)));
        return time;
    }
}
