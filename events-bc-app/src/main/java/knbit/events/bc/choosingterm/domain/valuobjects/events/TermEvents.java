package knbit.events.bc.choosingterm.domain.valuobjects.events;

import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TermEvents {

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class TermAdded {

        EventId eventId;
        Term term;
    }

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class TermRemoved {

        EventId eventId;
        Term term;
    }
}
