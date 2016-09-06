package knbit.events.bc.interest.domain.valueobjects.events.surveystarting.factory;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyStartingEvents;

/**
 * Created by novy on 28.05.15.
 */
public class BasicSurveyStartedEventFactory implements SurveyStartedEventFactory {

    @Override
    public SurveyStartingEvents.Started newSurveyingInterestStartedEvent(EventId eventId,
                                                                         EventDetails eventDetails,
                                                                         InterestPolicy thresholdPolicy) {

        return SurveyStartingEvents.Started.of(eventId, eventDetails, thresholdPolicy);
    }
}
