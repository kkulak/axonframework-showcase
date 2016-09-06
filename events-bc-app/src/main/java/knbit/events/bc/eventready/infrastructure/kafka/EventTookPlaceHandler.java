package knbit.events.bc.eventready.infrastructure.kafka;

import knbit.events.bc.common.Profiles;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 01.11.15.
 */

@Profile(Profiles.PROD)
@Component
public class EventTookPlaceHandler {

    private final KafkaPublisher publisher;

    @Autowired
    public EventTookPlaceHandler(KafkaPublisher publisher) {
        this.publisher = publisher;
    }

    @EventHandler
    public void on(ReadyEvents.TookPlace event) {
        final EventTookPlace eventTookPlace = EventTookPlace.from(event);
        publisher.publish(eventTookPlace);
    }
}