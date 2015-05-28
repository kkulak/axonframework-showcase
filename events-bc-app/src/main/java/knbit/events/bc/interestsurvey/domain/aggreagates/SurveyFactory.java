package knbit.events.bc.interestsurvey.domain.aggreagates;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interestsurvey.domain.policies.InterestThresholdPolicy;
import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;
import knbit.events.bc.interestsurvey.domain.valueobjects.events.surveycreation.BasicSurveyCreatedEventFactory;
import knbit.events.bc.interestsurvey.domain.valueobjects.events.surveycreation.EndingDateAwareSurveyCreatedEventFactory;
import knbit.events.bc.interestsurvey.domain.valueobjects.events.surveycreation.SurveyCreatedEventFactory;
import org.joda.time.DateTime;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */
public class SurveyFactory {

    public static Survey newSurvey(SurveyId surveyId, EventId eventId,
                                   Optional<Integer> minimalInterestThreshold,
                                   Optional<DateTime> endingSurveyDate) {

        final InterestThresholdPolicy interestThresholdPolicy =
                createInterestThresholdPolicy(minimalInterestThreshold);
        final SurveyCreatedEventFactory surveyCreatedEventFactory =
                createSurveyCreatedEventFactory(endingSurveyDate);

        return new Survey(
                surveyId, eventId, interestThresholdPolicy, surveyCreatedEventFactory
        );


    }

    private static InterestThresholdPolicy createInterestThresholdPolicy(Optional<Integer> minimalInterestThreshold) {
        // todo: implement
        return null;
    }

    private static SurveyCreatedEventFactory createSurveyCreatedEventFactory(Optional<DateTime> endingSurveyDate) {
        return endingSurveyDate.isPresent() ?
                new EndingDateAwareSurveyCreatedEventFactory(endingSurveyDate.get()) : new BasicSurveyCreatedEventFactory();
    }
}
