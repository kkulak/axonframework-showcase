package knbit.events.bc.interest.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.readmodel.EventStatus;
import knbit.events.bc.common.readmodel.EventStatusAware;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InterestAwareEvents {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Created implements EventStatusAware {

        private final EventId eventId;
        private final EventDetails eventDetails;

        @Override
        public EventStatus status() {
            return EventStatus.SURVEY_INTEREST;
        }
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class TransitedToUnderChoosingTerm {

        EventId eventId;
        EventDetails eventDetails;
    }
}
