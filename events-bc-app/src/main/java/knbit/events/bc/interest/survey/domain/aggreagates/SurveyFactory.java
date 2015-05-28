package knbit.events.bc.interest.survey.domain.aggreagates;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation.EndingDateAwareSurveyCreatedEventFactory;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import knbit.events.bc.interest.survey.domain.policies.InterestThresholdTurnedOffPolicy;
import knbit.events.bc.interest.survey.domain.policies.WithFixedThresholdPolicy;
import knbit.events.bc.interest.survey.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation.BasicSurveyCreatedEventFactory;
import knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation.SurveyCreatedEventFactory;
import org.joda.time.DateTime;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */
public class SurveyFactory {

    public static Survey newSurvey(SurveyId surveyId, EventId eventId,
                                   Optional<Integer> minimalInterestThreshold,
                                   Optional<DateTime> endingSurveyDate) {

        final InterestPolicy interestPolicy =
                createInterestThresholdPolicy(minimalInterestThreshold);
        final SurveyCreatedEventFactory surveyCreatedEventFactory =
                createSurveyCreatedEventFactory(endingSurveyDate);

        return new Survey(
                surveyId, eventId, interestPolicy, surveyCreatedEventFactory
        );


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
