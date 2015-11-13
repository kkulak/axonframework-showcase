package knbit.events.bc.interest.infrastructure.notifications;

import knbit.events.bc.common.config.AMQPConstants;
import knbit.events.bc.common.dispatcher.MessageDispatcher;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyingTimeExceededEvent;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 13.11.15.
 */

@Component
public class SurveyNotificationHandler {

    private final MessageDispatcher dispatcher;

    @Autowired
    public SurveyNotificationHandler(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @EventHandler
    public void on(SurveyEvents.InterestThresholdReached event) {
        final EventId eventId = event.eventId();
        final EventDetails details = event.eventDetails();

        dispatchNotification(
                new SurveyNotifications.InterestThresholdReached(eventId.value(), details.name().value()),
                SurveyNotificationType.INTEREST_THRESHOLD_REACHED.name()
        );
    }

    @EventHandler
    public void on(SurveyingTimeExceededEvent event) {
        final EventId eventId = event.eventId();
        final EventDetails details = event.eventDetails();

        dispatchNotification(
                new SurveyNotifications.SurveyingTimeExceeded(eventId.value(), details.name().value()),
                SurveyNotificationType.SURVEYING_TIME_EXCEEDED.name()
        );
    }

    private void dispatchNotification(SurveyNotifications.SurveyInterestNotification notification,
                                      String notificationType) {

        dispatcher.dispatch(notification, AMQPConstants.NOTIFICATION_QUEUE, notificationType);
    }
}
