package knbit.events.bc.choosingterm.domain.sagas

import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.common.config.AMQPConstants
import knbit.events.bc.common.dispatcher.MessageDispatcher
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.eventproposal.domain.sagas.ReservationNotifications
import knbit.events.bc.interest.builders.EventDetailsBuilder
import spock.lang.Specification

class ReservationNotificationSagaUnitTest extends Specification {
    def MessageDispatcher dispatcher
    def ReservationNotificationSaga objectUnderTest
    def EventId eventId = EventId.of("event-id")
    def ReservationId reservationId = ReservationId.of("res-id")

    def setup() {
        dispatcher = Mock(MessageDispatcher)
        objectUnderTest = new ReservationNotificationSaga()
        objectUnderTest.setDispatcher(dispatcher)
    }

    def "should call dispatcher given reservation accepted event"() {
        given:
        objectUnderTest.handle(UnderChoosingTermEventEvents.Created.of(eventId, EventDetailsBuilder.instance().build()))
        when:
        objectUnderTest.handle(ReservationEvents.ReservationAccepted.of(eventId, reservationId))
        then:
        1 * dispatcher.dispatch(ReservationNotifications.Accepted.of("event-id", "res-id", "name"), AMQPConstants.NOTIFICATION_QUEUE, "RESERVATION_ACCEPTED")
    }

    def "should call dispatcher given reservation rejected event"() {
        given:
        objectUnderTest.handle(UnderChoosingTermEventEvents.Created.of(eventId, EventDetailsBuilder.instance().build()))
        when:
        objectUnderTest.handle(ReservationEvents.ReservationRejected.of(eventId, reservationId))
        then:
        1 * dispatcher.dispatch(ReservationNotifications.Rejected.of("event-id", "res-id", "name"), AMQPConstants.NOTIFICATION_QUEUE, "RESERVATION_REJECTED")
    }

    def "should call dispatcher given reservation failed event"() {
        given:
        objectUnderTest.handle(UnderChoosingTermEventEvents.Created.of(eventId, EventDetailsBuilder.instance().build()))
        when:
        objectUnderTest.handle(ReservationEvents.ReservationFailed.of(eventId, reservationId, "cause"))
        then:
        1 * dispatcher.dispatch(ReservationNotifications.Failed.of("event-id", "res-id", "name", "cause"), AMQPConstants.NOTIFICATION_QUEUE, "RESERVATION_FAILED")
    }


}
