package knbit.events.bc.common.domain;

import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId;

/**
 * Created by novy on 04.10.15.
 */
public class IdFactory {

    public static EventId eventId() {
        return new EventId();
    }

    public static ReservationId reservationId() {
        return new ReservationId();
    }

    public static TermId termId() {
        return new TermId();
    }

    public static ReadyEventId readyEventId() {
        return new ReadyEventId();
    }
}
