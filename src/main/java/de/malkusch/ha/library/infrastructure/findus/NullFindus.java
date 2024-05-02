package de.malkusch.ha.library.infrastructure.findus;

import static de.malkusch.ha.library.model.RentedMedia.EMPTY;

import de.malkusch.ha.library.model.Findus;
import de.malkusch.ha.library.model.RentedMedia;
import lombok.extern.slf4j.Slf4j;

@Slf4j
final class NullFindus implements Findus {

    @Override
    public RentedMedia rentedMedia() {
        log.warn("Returning empty media from NullFindus");
        return EMPTY;
    }

}
