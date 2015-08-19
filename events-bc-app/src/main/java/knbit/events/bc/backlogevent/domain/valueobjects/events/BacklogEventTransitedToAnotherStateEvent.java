package knbit.events.bc.backlogevent.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;

/**
 * Created by novy on 16.08.15.
 */
public interface BacklogEventTransitedToAnotherStateEvent {

    EventId eventId();

    EventDetails eventDetails();
}
