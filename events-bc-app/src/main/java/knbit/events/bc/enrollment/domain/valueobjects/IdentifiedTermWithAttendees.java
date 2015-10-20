package knbit.events.bc.enrollment.domain.valueobjects;

import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Created by novy on 20.10.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class IdentifiedTermWithAttendees {

    TermId termId;
    EventDuration duration;
    ParticipantsLimit limit;
    Location location;
    Lecturer lecturer;
    Collection<Attendee> attendees;
}
