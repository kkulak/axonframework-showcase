package knbit.events.bc.common.readmodel;

import knbit.events.bc.common.domain.valueobjects.EventId;

public interface EventStatusAware {

    EventId eventId();

    EventStatus status();

}
