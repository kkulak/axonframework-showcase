package knbit.events.bc.choosingterm.domain.valuobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

public class TermStatusEvents {

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class Ready {
        EventId eventId;
    }

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class Pending {
        EventId eventId;
    }

}
