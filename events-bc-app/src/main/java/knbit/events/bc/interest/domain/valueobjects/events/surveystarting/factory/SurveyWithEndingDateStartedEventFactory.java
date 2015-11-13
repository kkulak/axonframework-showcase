package knbit.events.bc.interest.domain.valueobjects.events.surveystarting.factory;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyStartingEvents;
import org.joda.time.DateTime;

/**
 * Created by novy on 28.05.15.
 */
public class SurveyWithEndingDateStartedEventFactory
        implements SurveyStartedEventFactory {

    private final DateTime endingSurveyDate;

    public SurveyWithEndingDateStartedEventFactory(DateTime endingSurveyDate) {
        this.endingSurveyDate = endingSurveyDate;
    }

    @Override
    public SurveyStartingEvents.Started newSurveyingInterestStartedEvent(EventId eventId, EventDetails eventDetails, InterestPolicy thresholdPolicy) {
        return SurveyStartingEvents.StartedWithEndingDate.of(eventId, eventDetails, thresholdPolicy, endingSurveyDate);
    }
}
