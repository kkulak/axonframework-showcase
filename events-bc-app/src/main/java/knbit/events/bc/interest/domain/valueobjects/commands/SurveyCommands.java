package knbit.events.bc.interest.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.Optional;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SurveyCommands {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Start {

        EventId eventId;
        Optional<Integer> minimalInterestThreshold;
        Optional<DateTime> endingSurveyDate;
    }

    @Accessors(fluent = true)
    @Value
    public static class VoteUp {

        EventId eventId;
        Attendee attendee;
    }

    @Accessors(fluent = true)
    @Value
    public static class VoteDown {

        EventId eventId;
        Attendee attendee;
    }
}
