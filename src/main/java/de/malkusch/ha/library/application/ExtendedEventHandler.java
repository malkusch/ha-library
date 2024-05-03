package de.malkusch.ha.library.application;

import java.io.IOException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import de.malkusch.ha.library.model.Calendar;
import de.malkusch.ha.library.model.Entry;
import de.malkusch.ha.library.model.Extended;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class ExtendedEventHandler {

    private final Calendar calendar;

    @EventListener
    public void onExtended(Extended event) throws IOException {
        var entry = new Entry( //
                event.medium(), //
                String.format("Büchereirückgabe: %s", event.title()), //
                event.extension());

        calendar.deleteUpcoming(event.medium());
        calendar.add(entry);
    }
}
