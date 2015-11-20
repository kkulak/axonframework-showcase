package knbit.events.bc.enrollment.domain.valueobjects.commands;

import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Created by novy on 02.10.15.
 */

public interface EventUnderEnrollmentCommands {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class Create {

        EventId eventId;
        EventDetails eventDetails;
        Collection<EnrollmentIdentifiedTerm> terms;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class TransitToReady {

        EventId eventId;
    }
}
