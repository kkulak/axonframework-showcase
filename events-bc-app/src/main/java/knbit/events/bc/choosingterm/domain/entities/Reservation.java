package knbit.events.bc.choosingterm.domain.entities;

import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationAcceptedEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationCancelledEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationRejectedEvent;
import knbit.events.bc.common.domain.IdentifiedDomainEntity;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.stream.Stream;

/**
 * Created by novy on 19.08.15.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(fluent = true)
public class Reservation extends IdentifiedDomainEntity<ReservationId> {

    private EventId eventId;
    private ReservationId reservationId;

    @Getter
    private EventDuration eventDuration;
    @Getter
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

    private boolean concernedWith(ReservationEvent event) {
        return id.equals(event.reservationId());
    }

    public void accept() {
        rejectOn(ReservationStatus.ACCEPTED, ReservationStatus.REJECTED, ReservationStatus.CANCELLED);
        apply(ReservationAcceptedEvent.of(eventId, id));
    }

    public void reject() {
        rejectOn(ReservationStatus.ACCEPTED, ReservationStatus.REJECTED, ReservationStatus.CANCELLED);
        apply(ReservationRejectedEvent.of(eventId, id));
    }

    private void rejectOn(ReservationStatus... statuses) {
        Stream.of(statuses)
                .filter(possibleStatus -> possibleStatus == reservationStatus)
                .findAny()
                .ifPresent(invalidStatus -> invalidStatus.rejectOn(id));
    }

    @EventSourcingHandler
    private void on(ReservationAcceptedEvent event) {
        changeStatusTo(event, ReservationStatus.ACCEPTED);
    }

    @EventSourcingHandler
    private void on(ReservationRejectedEvent event) {
        changeStatusTo(event, ReservationStatus.REJECTED);
    }

    @EventSourcingHandler
    private void on(ReservationCancelledEvent event) {
        changeStatusTo(event, ReservationStatus.CANCELLED);
    }

    private void changeStatusTo(ReservationEvent event, ReservationStatus newStatus) {
        if (concernedWith(event)) {
            reservationStatus = newStatus;
        }
    }
}

