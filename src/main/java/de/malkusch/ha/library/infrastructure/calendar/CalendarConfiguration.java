package de.malkusch.ha.library.infrastructure.calendar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.malkusch.ha.library.model.Calendar;

@Configuration
class CalendarConfiguration {

    @Bean
    Calendar calendar() {
        return new NullCalendar();
    }
}
