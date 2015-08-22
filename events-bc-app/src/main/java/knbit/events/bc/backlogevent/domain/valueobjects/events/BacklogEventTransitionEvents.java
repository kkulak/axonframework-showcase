package knbit.events.bc.backlogevent.domain.valueobjects.events;

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
public final class BacklogEventTransitionEvents {

    public interface TransitedToAnotherState {

        EventId eventId();

        EventDetails eventDetails();
    }

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class TransitedToInterestAware implements TransitedToAnotherState {

        EventId eventId;
        EventDetails eventDetails;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class TransitedToUnderChoosingTerm implements TransitedToAnotherState {

        EventId eventId;
        EventDetails eventDetails;
    }
}
