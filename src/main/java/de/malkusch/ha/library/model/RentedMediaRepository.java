package de.malkusch.ha.library.model;

import de.malkusch.ha.library.model.RentedMedia.Diff;

public interface RentedMediaRepository {

    Diff sync(RentedMedia rentedMedia);
}
