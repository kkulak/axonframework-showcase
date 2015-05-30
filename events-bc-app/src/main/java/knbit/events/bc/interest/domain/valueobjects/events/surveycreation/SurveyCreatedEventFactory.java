package knbit.events.bc.interest.domain.valueobjects.events.surveycreation;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;

/**
 * Created by novy on 28.05.15.
 */
public interface SurveyCreatedEventFactory {

    SurveyCreatedEvent newSurveyCreatedEvent(SurveyId surveyId, EventId eventId, InterestPolicy thresholdPolicy);
}
