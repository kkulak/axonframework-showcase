package knbit.events.bc.choosingterm.domain.valuobjects.events;

import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm;
import knbit.events.bc.common.domain.valueobjects.EventCancelled;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnderChoosingTermEventEvents {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Created {

        EventId eventId;
        EventDetails eventDetails;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Cancelled implements EventCancelled {

        EventId eventId;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class TransitedToEnrollment {

        EventId eventId;
        EventDetails eventDetails;
        Collection<EnrollmentIdentifiedTerm> terms;
    }
}
