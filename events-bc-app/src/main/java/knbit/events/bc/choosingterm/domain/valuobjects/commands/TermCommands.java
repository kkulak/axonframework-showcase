package knbit.events.bc.choosingterm.domain.valuobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TermCommands {

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class AddTerm {

        EventId eventId;
        DateTime startDate;
        Duration duration;
        int capacity;
        String location;
    }

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class RemoveTerm {

        EventId eventId;
        TermId termId;
    }
}
