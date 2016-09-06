package knbit.events.bc.common.domain.mailnotifications;

import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.infrastructure.mailnotifications.NotificationBCClient;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @EventHandler
    public void on(ReadyEvents.Cancelled event) {
        notificationBCClient.sendNotificationTo(
                idsFromAttendees(event.attendees()),
                notificationFactory.readyEventCancelled(event.eventDetails())
        );
    }

    @EventHandler
    public void on(EventUnderEnrollmentEvents.Cancelled event) {
        notificationBCClient.sendNotificationTo(
                idsFromFromTerms(event.termsWithAttendees()),
                notificationFactory.enrollmentEventCancelled(event.eventDetails())
        );
    }

    private Collection<MemberId> idsFromAttendees(Collection<Attendee> attendees) {
        return idsFrom(attendees.stream());
    }

    private Collection<MemberId> idsFromFromTerms(Collection<IdentifiedTermWithAttendees> attendees) {
        final Stream<Attendee> allAttendees = attendees
                .stream()
                .flatMap(term -> term.attendees().stream());

        return idsFrom(allAttendees);
    }

    private Collection<MemberId> idsFrom(Stream<Attendee> attendees) {
        return attendees
                .map(Attendee::memberId)
                .collect(Collectors.toList());
    }
}
