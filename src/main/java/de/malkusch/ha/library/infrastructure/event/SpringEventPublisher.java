package de.malkusch.ha.library.infrastructure.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
final class SpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(Event event) {
        log.debug("publish {}", event);
        publisher.publishEvent(event);
    }

    @Override
    public String toString() {
        return "SpringEventPublisher";
    }
}
