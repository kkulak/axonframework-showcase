package knbit.events.bc.interest.infrastructure.notifications

import knbit.events.bc.common.config.AMQPConstants
import knbit.events.bc.common.dispatcher.MessageDispatcher
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.interest.builders.SurveyingTimeExceededEventBuilder
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents
import spock.lang.Specification

/**
 * Created by novy on 13.11.15.
 */
class SurveyNotificationHandlerTest extends Specification {

    def SurveyNotificationHandler objectUnderTest
    def MessageDispatcher messageDispatcherMock

    void setup() {
        messageDispatcherMock = Mock(MessageDispatcher)
        objectUnderTest = new SurveyNotificationHandler(messageDispatcherMock)
    }

    def "should dispatch notification of type INTEREST_THRESHOLD_REACHED on proper event "() {
        given:
        def eventId = EventId.of("anId")
        def eventDetails = EventDetailsBuilder.defaultEventDetails()

        def thresholdReachedEvent = SurveyEvents.InterestThresholdReached.of(eventId, eventDetails)
        def expectedNotification = new SurveyNotifications.InterestThresholdReached(
                eventId.value(), eventDetails.name().value()
        )

        when:
        objectUnderTest.on thresholdReachedEvent

        then:
        1 * messageDispatcherMock.dispatch(
                expectedNotification,
                AMQPConstants.NOTIFICATION_QUEUE,
                SurveyNotificationType.INTEREST_THRESHOLD_REACHED.name()
        )
    }

    def "should dispatch notification of type SURVEYING_TIME_EXCEEDED on proper event "() {
        given:
        def eventId = EventId.of("anId")
        def eventDetails = EventDetailsBuilder.defaultEventDetails()

        def timeExceededEvent = SurveyingTimeExceededEventBuilder
                .instance()
                .eventId(eventId)
                .eventDetails(eventDetails)
                .build()

        def expectedNotification = new SurveyNotifications.SurveyingTimeExceeded(
                eventId.value(), eventDetails.name().value()
        )

        when:
        objectUnderTest.on timeExceededEvent

        then:
        1 * messageDispatcherMock.dispatch(
                expectedNotification,
                AMQPConstants.NOTIFICATION_QUEUE,
                SurveyNotificationType.SURVEYING_TIME_EXCEEDED.name()
        )
    }
}
