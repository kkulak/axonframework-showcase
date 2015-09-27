package knbit.events.bc.choosingterm.domain.sagas;

import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermStatusEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.valueobjects.EventId;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventTemplate;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

public class TermStatusSaga extends AbstractAnnotatedSaga {
    private EventId eventId;

    private int termsAmount;
    private int reservationAmount;

    private transient EventTemplate eventTemplate;

    @StartSaga
    @SagaEventHandler(associationProperty = "eventId")
    public void handle(UnderChoosingTermEventEvents.Created event) {
        this.eventId = event.eventId();
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(TermEvents.TermAdded event) {
        termsAmount++;
        sendAppropriateEvent();
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(TermEvents.TermRemoved event) {
        termsAmount--;
        sendAppropriateEvent();
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(ReservationEvents.RoomRequested event) {
        reservationAmount++;
        sendAppropriateEvent();
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(ReservationEvents.ReservationAccepted event) {
        reservationAmount--;
        sendAppropriateEvent();
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(ReservationEvents.ReservationCancelled event) {
        reservationAmount--;
        sendAppropriateEvent();
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(ReservationEvents.ReservationRejected event) {
        reservationAmount--;
        sendAppropriateEvent();
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(ReservationEvents.ReservationFailed event) {
        reservationAmount--;
        sendAppropriateEvent();
    }

    // TODO: end saga event!! (on transition from choosing term state to another)

    private void sendAppropriateEvent() {
        if(termsAmount > 0 && reservationAmount == 0)
            eventTemplate.publishEvent(TermStatusEvents.Ready.of(eventId));
        else
            eventTemplate.publishEvent(TermStatusEvents.Pending.of(eventId));
    }

    @Autowired
    public void setEventTemplate(EventBus eventBus) {
        this.eventTemplate = new EventTemplate(eventBus);
    }

}
