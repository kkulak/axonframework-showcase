package knbit.events.bc.choosingterm.domain.entities;

import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvent;
import knbit.events.bc.common.domain.IdentifiedDomainEntity;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by novy on 19.08.15.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation extends IdentifiedDomainEntity<ReservationId> {

    private EventId eventId;
    private ReservationId reservationId;
    private EventDuration eventDuration;
    private Capacity capacity;
    private ReservationStatus reservationStatus;

    public Reservation(EventId eventId, ReservationId reservationId, EventDuration eventDuration, Capacity capacity) {
        this.eventId = eventId;
        this.id = reservationId;
        this.eventDuration = eventDuration;
        this.capacity = capacity;
        // todo shouldn't we wait for an external confirmation?
        this.reservationStatus = ReservationStatus.PENDING;
    }

    private boolean notConcernedWith(ReservationEvent event) {
        return !id.equals(event.reservationId());
    }

    private enum ReservationStatus {
        PENDING, ACCEPTED, REJECTED
    }
}

