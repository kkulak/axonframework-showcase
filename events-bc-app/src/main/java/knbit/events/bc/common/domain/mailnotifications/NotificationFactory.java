package knbit.events.bc.common.domain.mailnotifications;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.infrastructure.mailnotifications.Notification;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 06.12.15.
 */

@Component
class NotificationFactory {

    private final TemplateReader reader;

    @Autowired
    public NotificationFactory(TemplateReader reader) {
        this.reader = reader;
    }

    public Notification newSurveyingEventMessageFrom(EventDetails eventDetails) {
        final String messageContent = reader
                .templateFrom("emails/new_surveying_event.ftl")
                .fillWith(
                        TemplateDataAssembler
                                .prepopulate(DetailsParser.parse(eventDetails))
                                .data()
                );

        return new Notification("New event proposal!", messageContent);
    }

    public Notification newEnrollmentFrom(EventDetails eventDetails) {
        final String messageContent = reader
                .templateFrom("emails/new_upcoming_event.ftl")
                .fillWith(
                        TemplateDataAssembler
                                .prepopulate(DetailsParser.parse(eventDetails))
                                .data()
                );

        return new Notification("New upcoming event!", messageContent);
    }

    public Notification enrollmentEventCancelled(EventDetails details) {
        final String messageContent = reader
                .templateFrom("emails/enrollment_cancelled.ftl")
                .fillWith(
                        TemplateDataAssembler
                                .prepopulate(DetailsParser.parse(details))
                                .data()
                );

        return new Notification("Enrollment cancelled", messageContent);
    }

    public Notification readyEventCancelled(EventReadyDetails details) {
        final String messageContent = reader
                .templateFrom("emails/event_cancelled.ftl")
                .fillWith(
                        TemplateDataAssembler
                                .prepopulate(DetailsParser.parse(details))
                                .data()
                );

        return new Notification("Event cancelled", messageContent);
    }
}
