package knbit.events.bc.eventready.builders;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Collection;

/**
 * Created by novy on 30.10.15.
 */


@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class EventReadyDetailsBuilder {

    private EventDetails eventDetails = EventDetailsBuilder.defaultEventDetails();
    private EventDuration duration = EventDuration.of(DateTime.now(), Duration.standardHours(1));
    private ParticipantsLimit limit = ParticipantsLimit.of(100);
    private Location location = Location.of("3.27A");
    private Collection<Lecturer> lecturers = ImmutableList.of(
            Lecturer.of("John Doe", "john-doe")
    );

    public EventReadyDetails build() {
        return EventReadyDetails.of(eventDetails, duration, limit, location, lecturers);
    }

    public static EventReadyDetails defaultEventDetails() {
        return EventReadyDetailsBuilder.instance().build();
    }
}
