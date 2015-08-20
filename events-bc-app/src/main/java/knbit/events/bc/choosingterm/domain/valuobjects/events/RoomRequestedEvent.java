package knbit.events.bc.choosingterm.domain.valuobjects.events;

import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 19.08.15.
 */

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class RoomRequestedEvent implements ReservationEvent {

    EventId eventId;
    ReservationId reservationId;
    EventDuration eventDuration;
    Capacity capacity;
}
