package knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation;

import knbit.events.bc.backlogevent.domain.valueobjects.EventId;
import knbit.events.bc.interest.survey.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;

/**
 * Created by novy on 28.05.15.
 */
public interface SurveyCreatedEventFactory {

    SurveyCreatedEvent newSurveyCreatedEvent(SurveyId surveyId, EventId eventId, InterestPolicy thresholdPolicy);
}
