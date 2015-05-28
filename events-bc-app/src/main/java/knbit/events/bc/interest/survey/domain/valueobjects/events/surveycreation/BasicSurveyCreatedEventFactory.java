package knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.survey.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;

/**
 * Created by novy on 28.05.15.
 */
public class BasicSurveyCreatedEventFactory implements SurveyCreatedEventFactory {

    @Override
    public SurveyCreatedEvent newSurveyCreatedEvent(SurveyId surveyId, EventId eventId, InterestPolicy thresholdPolicy) {
        return new SurveyCreatedEvent(surveyId, eventId, thresholdPolicy);
    }
}
