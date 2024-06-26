package de.malkusch.ha.library.application;

import java.io.IOException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import de.malkusch.ha.library.model.Calendar;
import de.malkusch.ha.library.model.Entry;
import de.malkusch.ha.library.model.Rented;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class RentedEventHandler {

    private final Calendar calendar;

    @EventListener
    public void onRented(Rented event) throws IOException {
        var entry = new Entry( //
                event.id(), //
                String.format("Büchereirückgabe: %s", event.title()), //
                event.until());

        calendar.deleteUpcoming(event.id());
        calendar.add(entry);
    }
}
