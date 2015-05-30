package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.domain.valueobjects.events.surveycreation.BasicSurveyCreatedEventFactory;
import knbit.events.bc.interest.domain.valueobjects.events.surveycreation.EndingDateAwareSurveyCreatedEventFactory;
import knbit.events.bc.interest.domain.valueobjects.events.surveycreation.SurveyCreatedEventFactory;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import knbit.events.bc.interest.survey.domain.policies.InterestThresholdTurnedOffPolicy;
import knbit.events.bc.interest.survey.domain.policies.WithFixedThresholdPolicy;
import org.joda.time.DateTime;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */
public class SurveyFactory {

    public static InterestAwareEvent newSurvey(SurveyId surveyId, EventId eventId,
                                               Optional<Integer> minimalInterestThreshold,
                                               Optional<DateTime> endingSurveyDate) {

        final InterestPolicy interestPolicy =
                createInterestThresholdPolicy(minimalInterestThreshold);
        final SurveyCreatedEventFactory surveyCreatedEventFactory =
                createSurveyCreatedEventFactory(endingSurveyDate);

//        return new InterestAwareEvent(
//                surveyId, eventId, interestPolicy, surveyCreatedEventFactory
//        );
        return null;


    }

    private static InterestPolicy createInterestThresholdPolicy(Optional<Integer> minimalInterestThreshold) {
        return minimalInterestThreshold.isPresent() ?
                new WithFixedThresholdPolicy(minimalInterestThreshold.get()) : new InterestThresholdTurnedOffPolicy();
    }

    private static SurveyCreatedEventFactory createSurveyCreatedEventFactory(Optional<DateTime> endingSurveyDate) {
        return endingSurveyDate.isPresent() ?
                new EndingDateAwareSurveyCreatedEventFactory(endingSurveyDate.get()) : new BasicSurveyCreatedEventFactory();
    }
}
