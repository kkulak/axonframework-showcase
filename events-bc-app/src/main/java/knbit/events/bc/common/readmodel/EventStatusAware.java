package knbit.events.bc.common.readmodel;

import knbit.events.bc.backlogevent.domain.valueobjects.EventId;

public interface EventStatusAware {

    EventId eventId();
    EventStatus status();

}
