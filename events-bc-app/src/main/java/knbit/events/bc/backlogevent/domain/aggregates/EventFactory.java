package knbit.events.bc.backlogevent.domain.aggregates;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.backlogevent.domain.valueobjects.EventId;

public class EventFactory {

    public static BacklogEvent newEvent(
            EventId id, EventDetails eventDetails) {
        return new BacklogEvent(id, eventDetails);
    }

}
