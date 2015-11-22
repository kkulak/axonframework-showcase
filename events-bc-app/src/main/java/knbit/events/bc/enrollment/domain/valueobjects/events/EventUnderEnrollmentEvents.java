package knbit.events.bc.enrollment.domain.valueobjects.events;

import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm;
import knbit.events.bc.common.domain.valueobjects.EventCancelled;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Created by novy on 02.10.15.
 */

public interface EventUnderEnrollmentEvents {


    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class Created {

        EventId eventId;
        EventDetails eventDetails;
        Collection<EnrollmentIdentifiedTerm> terms;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class TransitedToReady {

        EventId eventId;
        EventDetails eventDetails;
        Collection<IdentifiedTermWithAttendees> terms;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class Cancelled implements EventCancelled {

        EventId eventId;
        Collection<IdentifiedTermWithAttendees> termsWithAttendees;
    }
}
