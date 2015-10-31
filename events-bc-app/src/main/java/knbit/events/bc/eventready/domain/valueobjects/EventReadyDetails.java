package knbit.events.bc.eventready.domain.valueobjects;

import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

/**
 * Created by novy on 30.10.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class EventReadyDetails {

    @Delegate
    EventDetails eventDetails;
    EventDuration duration;
    ParticipantsLimit limit;
    Location location;
    Lecturer lecturer;
}
