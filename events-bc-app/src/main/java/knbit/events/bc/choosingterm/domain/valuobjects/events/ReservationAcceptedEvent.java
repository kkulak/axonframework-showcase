package knbit.events.bc.choosingterm.domain.valuobjects.events;

import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 19.08.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class ReservationAcceptedEvent implements ReservationEvent {

    EventId eventId;
    ReservationId reservationId;
}
