package knbit.events.bc.event.domain.aggregates;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.event.domain.valueobjects.Description;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.valueobjects.Name;
import knbit.events.bc.common.domain.enums.EventType;

public class EventFactory {

    public static AbstractEvent newEvent(
            EventId id, String name, String description, EventType eventType, EventFrequency eventFrequency) {
        switch (eventFrequency) {
            case ONE_OFF:
                return new OneOffEvent(id, eventType, Name.of(name), Description.of(description), eventFrequency);
            case CYCLIC:
                return new CyclicEvent(id, eventType, Name.of(name), Description.of(description), eventFrequency);
            default:
                throw new IllegalArgumentException();
        }
    }

}
