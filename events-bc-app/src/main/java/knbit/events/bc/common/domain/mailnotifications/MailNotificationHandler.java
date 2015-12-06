package knbit.events.bc.common.domain.mailnotifications;

import knbit.events.bc.common.infrastructure.mailnotifications.NotificationBCClient;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 06.12.15.
 */

@Component
public class MailNotificationHandler {

    private final NotificationBCClient notificationBCClient;
    private final NotificationFactory notificationFactory;

    @Autowired
    public MailNotificationHandler(NotificationBCClient notificationBCClient, NotificationFactory notificationFactory) {
        this.notificationBCClient = notificationBCClient;
        this.notificationFactory = notificationFactory;
    }

    @EventHandler
    public void on(InterestAwareEvents.Created event) {
        notificationBCClient.sendNotificationToAllMembers(
                notificationFactory.newSurveyingEventMessageFrom(event.eventDetails())
        );
    }

    @EventHandler
    public void on(EventUnderEnrollmentEvents.Created event) {
        notificationBCClient.sendNotificationToAllMembers(
                notificationFactory.newEnrollmentFrom(event.eventDetails())
        );
    }
}
