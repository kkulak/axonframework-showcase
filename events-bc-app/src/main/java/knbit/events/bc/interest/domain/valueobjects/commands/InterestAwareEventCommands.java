package knbit.events.bc.interest.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InterestAwareEventCommands {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Create {

        EventId eventId;
        EventDetails eventDetails;

    }

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class TransitToUnderTermChoosingEvent {

        EventId eventId;
    }

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class Cancel {

        EventId eventId;
    }
}
