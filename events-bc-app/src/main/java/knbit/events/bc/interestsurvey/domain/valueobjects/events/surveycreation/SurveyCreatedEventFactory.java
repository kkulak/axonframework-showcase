package knbit.events.bc.interestsurvey.domain.valueobjects.events.surveycreation;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interestsurvey.domain.policies.InterestThresholdPolicy;
import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;

/**
 * Created by novy on 28.05.15.
 */
public interface SurveyCreatedEventFactory {

    SurveyCreatedEvent newSurveyCreatedEvent(SurveyId surveyId, EventId eventId, InterestThresholdPolicy thresholdPolicy);
}
