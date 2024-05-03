package de.malkusch.ha.library.model;

import java.io.IOException;
import java.time.LocalDate;

public interface Calendar {

    void add(Entry entry) throws IOException;

    void deleteUpcoming(MediumId id) throws IOException;

}
