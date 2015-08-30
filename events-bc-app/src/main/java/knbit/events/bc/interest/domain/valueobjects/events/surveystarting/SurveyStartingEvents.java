package knbit.events.bc.interest.domain.valueobjects.events.surveystarting;

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
        private final InterestPolicy interestPolicy;

        public static Started of(EventId eventId, InterestPolicy interestPolicy) {
            return new Started(eventId, interestPolicy);
        }

    }


    @Accessors(fluent = true)
    @Getter
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class StartedWithEndingDate extends Started {

        private final DateTime endingSurveyDate;

        public static StartedWithEndingDate of(EventId eventId,
                                                                InterestPolicy interestPolicy,
                                                                DateTime endingSurveyDate) {

            return new StartedWithEndingDate(eventId, interestPolicy, endingSurveyDate);
        }

        private StartedWithEndingDate(EventId eventId,
                                      InterestPolicy interestPolicy,
                                      DateTime endingSurveyDate) {
            super(eventId, interestPolicy);
            this.endingSurveyDate = endingSurveyDate;
        }
    }


}
