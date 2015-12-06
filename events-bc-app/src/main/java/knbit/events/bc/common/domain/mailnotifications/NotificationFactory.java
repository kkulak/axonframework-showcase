package knbit.events.bc.common.domain.mailnotifications;

import com.google.common.collect.ImmutableMap;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.URL;
import knbit.events.bc.common.infrastructure.mailnotifications.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

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
        final String messageContent =
                reader.templateFrom("templates/emails/new_surveying_event.ftl").fillWith(eventData(eventDetails));
        return new Notification("New event proposal!", messageContent);
    }

    public Notification newEnrollmentFrom(EventDetails eventDetails) {
        final String messageContent =
                reader.templateFrom("templates/emails/new_upcoming_event.ftl").fillWith(eventData(eventDetails));
        return new Notification("New upcoming event!", messageContent);
    }

    private Map<String, String> eventData(EventDetails details) {
        final ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();
        mapBuilder.put("eventName", details.name().value());
        mapBuilder.put("eventDescription", details.description().value());

        // todo: fix
        final Optional<URL> maybeUrl = details.imageUrl();
        maybeUrl.ifPresent(url -> mapBuilder.put("imageUrl", url.value()));

        return mapBuilder.build();
    }
}
