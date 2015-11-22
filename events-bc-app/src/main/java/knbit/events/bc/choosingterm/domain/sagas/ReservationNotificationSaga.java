package knbit.events.bc.choosingterm.domain.sagas;

import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.config.AMQPConstants;
import knbit.events.bc.common.dispatcher.MessageDispatcher;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.eventproposal.domain.sagas.ReservationNotifications;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationNotificationSaga extends AbstractAnnotatedSaga {
    private EventId eventId;
    private EventDetails eventDetails;

    private transient MessageDispatcher dispatcher;
    private static final String ACCEPTED_RESERVATION_NOTIFICATION = "RESERVATION_ACCEPTED";
    private static final String REJECTED_RESERVATION_NOTIFICATION = "RESERVATION_REJECTED";
    private static final String FAILED_RESERVATION_NOTIFICATION = "RESERVATION_FAILED";

    @StartSaga
    @SagaEventHandler(associationProperty = "eventId")
    public void handle(UnderChoosingTermEventEvents.Created event) {
        this.eventId = event.eventId();
        this.eventDetails = event.eventDetails();
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(ReservationEvents.ReservationAccepted event) {
        final ReservationNotifications.Accepted message = ReservationNotifications.Accepted.of(
                event.eventId().value(),
                event.reservationId().value(),
                eventDetails.name().value()
        );

        dispatcher.dispatch(message, AMQPConstants.NOTIFICATION_QUEUE, ACCEPTED_RESERVATION_NOTIFICATION);
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(ReservationEvents.ReservationRejected event) {
        final ReservationNotifications.Rejected message = ReservationNotifications.Rejected.of(
                event.eventId().value(),
                event.reservationId().value(),
                eventDetails.name().value()
        );

        dispatcher.dispatch(message, AMQPConstants.NOTIFICATION_QUEUE, REJECTED_RESERVATION_NOTIFICATION);
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(ReservationEvents.ReservationFailed event) {
        final ReservationNotifications.Failed message = ReservationNotifications.Failed.of(
                event.eventId().value(),
                event.reservationId().value(),
                eventDetails.name().value(),
                event.cause()
        );

        dispatcher.dispatch(message, AMQPConstants.NOTIFICATION_QUEUE, FAILED_RESERVATION_NOTIFICATION);
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(UnderChoosingTermEventEvents.TransitedToEnrollment event) {
        end();
    }

    @SagaEventHandler(associationProperty = "eventId")
    public void handle(UnderChoosingTermEventEvents.Cancelled event) {
        end();
    }

    @Autowired
    public void setDispatcher(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

}
