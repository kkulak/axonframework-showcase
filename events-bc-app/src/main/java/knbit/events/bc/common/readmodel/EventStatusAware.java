package knbit.events.bc.common.readmodel;

import knbit.events.bc.event.domain.valueobjects.EventId;

public interface EventStatusAware {

    EventId eventId();
    EventStatus status();

}
