package knbit.events.bc.interest.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.Attendee;
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
public final class SurveyEvents {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class InterestThresholdReached {

        EventId eventId;
        EventDetails eventDetails;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Ended {

        EventId eventId;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class VotedDown {

        EventId eventId;
        Attendee attendee;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class VotedUp {

        EventId eventId;
        Attendee attendee;
    }
}
