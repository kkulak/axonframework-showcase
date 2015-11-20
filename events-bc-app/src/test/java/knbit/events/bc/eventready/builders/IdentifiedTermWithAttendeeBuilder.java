package knbit.events.bc.eventready.builders;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Collection;

/**
 * Created by novy on 24.10.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class IdentifiedTermWithAttendeeBuilder {

    private TermId termId = TermId.of("termId");
    private EventDuration duration = EventDuration.of(
            new DateTime(2001, 9, 11, 0, 0), Duration.standardHours(2)
    );
    private ParticipantsLimit limit = ParticipantsLimit.of(100);
    private Location location = Location.of("default location");
    private Collection<Lecturer> lecturers = ImmutableList.of(
            Lecturer.of("John Doe", "john-doe")
    );
    private Collection<Attendee> attendees = ImmutableList.of(
            Attendee.of(MemberId.of("member1")), Attendee.of(MemberId.of("member2"))
    );

    public IdentifiedTermWithAttendees build() {
        return IdentifiedTermWithAttendees.of(
                termId, duration, limit, location, lecturers, attendees
        );
    }

    public static IdentifiedTermWithAttendees defaultTerm() {
        return IdentifiedTermWithAttendeeBuilder.instance().build();
    }
}
