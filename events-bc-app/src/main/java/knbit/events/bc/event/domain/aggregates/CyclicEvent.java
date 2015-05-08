package knbit.events.bc.event.domain.aggregates;

import knbit.events.bc.event.domain.enums.EventState;
import knbit.events.bc.event.domain.valueobjects.Description;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.valueobjects.Name;
import knbit.events.bc.event.domain.valueobjects.events.CyclicEventCreated;
import knbit.events.bc.common.domain.enums.EventType;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class CyclicEvent extends AbstractEvent {

    public CyclicEvent() { }

    public CyclicEvent(EventId id, EventType type, Name name, Description description) {
        apply(
                CyclicEventCreated.of(id, type, name, description, EventState.CREATED)
        );
    }

    @EventSourcingHandler
    private void on(CyclicEventCreated event) {
        id = event.eventId();
        type = event.eventType();
        name = event.name();
        description = event.description();
        state = event.eventState();
    }

}
