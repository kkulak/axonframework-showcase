package knbit.events.bc.event.domain.aggregates;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.event.domain.valueobjects.Description;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.enums.EventState;
import knbit.events.bc.event.domain.valueobjects.Name;
import knbit.events.bc.event.domain.valueobjects.events.OneOffEventCreated;
import knbit.events.bc.common.domain.enums.EventType;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class OneOffEvent extends AbstractEvent {

    public OneOffEvent() { }

    public OneOffEvent(EventId id, EventType type, Name name, Description description, EventFrequency frequency) {
        apply(
                OneOffEventCreated.of(id, type, name, description, EventState.CREATED, frequency)
        );
    }

    @EventSourcingHandler
    private void on(OneOffEventCreated event) {
        id = event.eventId();
        type = event.eventType();
        name = event.name();
        description = event.description();
        state = event.eventState();
        frequency = event.eventFrequency();
    }

}
