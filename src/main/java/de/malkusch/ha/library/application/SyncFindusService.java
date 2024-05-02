package de.malkusch.ha.library.application;

import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.malkusch.ha.library.infrastructure.event.EventPublisher;
import de.malkusch.ha.library.model.Extended;
import de.malkusch.ha.library.model.Findus;
import de.malkusch.ha.library.model.Rented;
import de.malkusch.ha.library.model.RentedMediaRepository;
import de.malkusch.ha.library.model.Returned;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SyncFindusService {

    private final EventPublisher events;
    private final Findus findus;
    private final RentedMediaRepository repository;

    @Scheduled(cron = "59 49 11,23 * * *")
    public void scheduledSync() throws IOException, InterruptedException {
        sync();
    }

    public void sync() throws IOException, InterruptedException {
        var diff = repository.sync(findus.rentedMedia());

        for (var added : diff.added()) {
            var event = new Rented(added.medium().id(), added.medium().title(), added.until());
            events.publish(event);
        }

        for (var removed : diff.removed()) {
            var event = new Returned(removed.medium().id());
            events.publish(event);
        }

        for (var changed : diff.changed()) {
            var event = new Extended(changed.medium().id(), changed.until());
            events.publish(event);
        }
    }

}
