package knbit.events.bc.interest.domain.valueobjects.events.surveystarting;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy;
import lombok.*;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SurveyStartingEvents {

    @Accessors(fluent = true)
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    @ToString
    public static class Started {

        private final EventId eventId;
        private final EventDetails eventDetails;
        private final InterestPolicy interestPolicy;

        public static Started of(EventId eventId, EventDetails eventDetails, InterestPolicy interestPolicy) {
            return new Started(eventId, eventDetails, interestPolicy);
        }

    }


    @Accessors(fluent = true)
    @Getter
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class StartedWithEndingDate extends Started {

        private final DateTime endingSurveyDate;

        public static StartedWithEndingDate of(EventId eventId,
                                               EventDetails eventDetails,
                                               InterestPolicy interestPolicy,
                                               DateTime endingSurveyDate) {

            return new StartedWithEndingDate(eventId, eventDetails, interestPolicy, endingSurveyDate);
        }

        private StartedWithEndingDate(EventId eventId,
                                      EventDetails eventDetails,
                                      InterestPolicy interestPolicy,
                                      DateTime endingSurveyDate) {
            super(eventId, eventDetails, interestPolicy);
            this.endingSurveyDate = endingSurveyDate;
        }
    }


}
