package de.malkusch.ha.library.model;

import java.io.IOException;

public interface Findus {

    RentedMedia rentedMedia() throws IOException, InterruptedException;

}
