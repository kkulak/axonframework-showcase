package knbit.events.bc.event.domain.valueobjects.events;

import knbit.events.bc.event.domain.enums.EventState;
import knbit.events.bc.event.domain.valueobjects.Description;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.valueobjects.Name;
import knbit.events.bc.common.domain.enums.EventType;

public class CyclicEventCreated extends EventCreated {

    private CyclicEventCreated(
            EventId eventId, EventType eventType, Name name, Description description, EventState eventState) {
        super(eventId, eventType, name, description, eventState);
    }

    public static CyclicEventCreated of(
            EventId eventId, EventType eventType, Name name, Description description, EventState eventState) {
        return new CyclicEventCreated(eventId, eventType, name, description, eventState);
    }

}
