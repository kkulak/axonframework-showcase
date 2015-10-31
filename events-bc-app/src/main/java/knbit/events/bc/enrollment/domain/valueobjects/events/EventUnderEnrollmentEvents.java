package knbit.events.bc.enrollment.domain.valueobjects.events;

import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm;
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
        Collection<IdentifiedTerm> terms;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class TransitedToReady {

        EventId eventId;
        EventDetails eventDetails;
        Collection<IdentifiedTermWithAttendees> terms;
    }
}
