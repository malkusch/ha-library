package de.malkusch.ha.library.infrastructure.rentedmediarepository;

import de.malkusch.ha.library.model.RentedMedia;
import de.malkusch.ha.library.model.RentedMedia.Diff;
import de.malkusch.ha.library.model.RentedMediaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
final class InMemoryRentedMediaRepository implements RentedMediaRepository {

    private volatile RentedMedia rentedMedia = RentedMedia.EMPTY;

    @Override
    public Diff sync(RentedMedia sync) {
        log.warn("Syncing RentedMedia in memory");
        var diff = rentedMedia.diff(sync);
        rentedMedia = sync;
        return diff;
    }
}
