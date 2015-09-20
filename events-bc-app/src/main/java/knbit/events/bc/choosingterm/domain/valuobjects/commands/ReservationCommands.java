package knbit.events.bc.choosingterm.domain.valuobjects.commands;

import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReservationCommands {

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class BookRoom {

        EventId eventId;
        DateTime startDate;
        Duration duration;
        int capacity;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class AcceptReservation {

        EventId eventId;
        ReservationId reservationId;
        String location;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class RejectReservation {

        EventId eventId;
        ReservationId reservationId;
        // todo: reason?

    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class CancelReservation {

        EventId eventId;
        ReservationId reservationId;

    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class FailReservation {

        EventId eventId;
        ReservationId reservationId;
        String reason;

    }

}
