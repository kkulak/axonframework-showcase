package knbit.events.bc.interest.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.Attendee;
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
public final class InterestAwareEvents {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Created {

        EventId eventId;
        EventDetails eventDetails;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class TransitedToUnderChoosingTerm {

        EventId eventId;
        EventDetails eventDetails;
    }

    public interface InterestAwareEventCancelled extends EventCancelled {
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class CancelledBeforeSurveyStarted implements InterestAwareEventCancelled {

        EventId eventId;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class CancelledDuringOrAfterSurveying implements InterestAwareEventCancelled {

        EventId eventId;
        Collection<Attendee> voters;
    }
}
