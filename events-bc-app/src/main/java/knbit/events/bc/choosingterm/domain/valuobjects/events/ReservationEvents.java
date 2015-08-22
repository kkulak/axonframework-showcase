package knbit.events.bc.choosingterm.domain.valuobjects.events;

import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReservationEvents {

    public interface ReservationEvent {

        ReservationId reservationId();
    }

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class RoomRequested implements ReservationEvent {

        EventId eventId;
        ReservationId reservationId;
        EventDuration eventDuration;
        Capacity capacity;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class ReservationAccepted implements ReservationEvent {

        EventId eventId;
        ReservationId reservationId;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class ReservationCancelled implements ReservationEvent {

        EventId eventId;
        ReservationId reservationId;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class ReservationRejected implements ReservationEvent {

        // todo: reason?
        EventId eventId;
        ReservationId reservationId;
    }
}
