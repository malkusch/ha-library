package de.malkusch.ha.library.application;

import java.io.IOException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import de.malkusch.ha.library.model.Calendar;
import de.malkusch.ha.library.model.Returned;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class ReturnedEventHandler {

    private final Calendar calendar;

    @EventListener
    public void onReturned(Returned event) throws IOException {
        calendar.deleteUpcoming(event.medium());
    }
}
