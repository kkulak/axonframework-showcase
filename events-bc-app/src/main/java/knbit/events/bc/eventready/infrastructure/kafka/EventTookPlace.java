package knbit.events.bc.eventready.infrastructure.kafka;

import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.Section;
import knbit.events.bc.common.domain.valueobjects.URL;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import org.joda.time.DateTime;
import pl.agh.knbit.generated.protobuffs.EventsBc;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by novy on 01.11.15.
 */
public class EventTookPlace {

    private final EventsBc.EventTookPlaceEvent protoBuf;

    public static EventTookPlace from(ReadyEvents.TookPlace domainEvent) {
        return new EventTookPlace(domainEvent);
    }

    private EventTookPlace(ReadyEvents.TookPlace domainEvent) {
        final ReadyEventId eventId = domainEvent.readyEventId();
        final EventReadyDetails eventDetails = domainEvent.eventDetails();
        final Collection<Attendee> attendees = domainEvent.attendees();

        protoBuf = EventsBc.EventTookPlaceEvent
                .newBuilder()
                .setEventId(eventId.value())
                .setEventName(eventDetails.name().value())
                .setEventDescription(eventDetails.description().value())
                .setUtcDateAsEpochSeconds(epochSecondsFrom(eventDetails.duration().start()))
                .setEventPictureUrl(imageFrom(eventDetails.imageUrl()))
                .setSectionId(sectionIdFrom(eventDetails.section()))
                .setEventType(typeFrom(eventDetails.type()))
                .addSpeakers(speakerFrom(eventDetails.lecturers()))
                .setAttendesCount(attendees.size())
                .build();
    }

    // TODO: change event definition in knbit-system-commons
    private EventsBc.EventTookPlaceEvent.Speaker speakerFrom(Collection<Lecturer> lecturer) {
        return EventsBc.EventTookPlaceEvent.Speaker
                .newBuilder()
                .build();
    }

    private String imageFrom(Optional<URL> imageUrl) {
        return imageUrl
                .map(URL::value)
                .orElse(null);
    }

    private String sectionIdFrom(Optional<Section> section) {
        return section
                .map(Section::id)
                .orElse(null);
    }

    private EventsBc.EventTookPlaceEvent.EventType typeFrom(EventType domainType) {
        return EventsBc.EventTookPlaceEvent.EventType.valueOf(domainType.name());
    }

    private long epochSecondsFrom(DateTime start) {
        return start.getMillis() / 1000;
    }

    public byte[] asBytes() {
        return protoBuf.toByteArray();
    }
}
