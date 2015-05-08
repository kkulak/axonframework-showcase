package knbit.events.bc.event.domain.valueobjects.events;

import knbit.events.bc.event.domain.valueobjects.Description;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.enums.EventState;
import knbit.events.bc.event.domain.valueobjects.Name;
import knbit.events.bc.common.domain.enums.EventType;

public class OneOffEventCreated extends EventCreated {

    private OneOffEventCreated(
            EventId eventId, EventType type, Name name, Description description, EventState state) {
        super(eventId, type, name, description, state);
    }

    public static OneOffEventCreated of(
            EventId eventId, EventType type, Name name, Description description, EventState state) {
        return new OneOffEventCreated(eventId, type,  name, description, state);
    }

}
