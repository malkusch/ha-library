package de.malkusch.ha.library.infrastructure.rentedmediarepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.malkusch.ha.library.model.RentedMediaRepository;

@Configuration
class RentedMediaRepositoryConfiguration {

    @Bean
    RentedMediaRepository rentedMediaRepository() {
        return new InMemoryRentedMediaRepository();
    }
}
