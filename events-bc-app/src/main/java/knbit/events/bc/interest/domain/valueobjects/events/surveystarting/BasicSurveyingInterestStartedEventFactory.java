package knbit.events.bc.interest.domain.valueobjects.events.surveystarting;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;

/**
 * Created by novy on 28.05.15.
 */
public class BasicSurveyingInterestStartedEventFactory implements SurveyingInterestStartedEventFactory {

    @Override
    public SurveyingInterestStartedEvent newSurveyingInterestStartedEvent(EventId eventId, InterestPolicy thresholdPolicy) {
        return SurveyingInterestStartedEvent.of(eventId, thresholdPolicy);
    }
}
