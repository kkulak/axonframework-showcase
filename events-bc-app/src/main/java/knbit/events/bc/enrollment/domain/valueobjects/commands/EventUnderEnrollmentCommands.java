package knbit.events.bc.enrollment.domain.valueobjects.commands;

import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Created by novy on 02.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventUnderEnrollmentCommands {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Create {

        EventId eventId;
        EventDetails eventDetails;
        Collection<Term> terms;
    }
}
