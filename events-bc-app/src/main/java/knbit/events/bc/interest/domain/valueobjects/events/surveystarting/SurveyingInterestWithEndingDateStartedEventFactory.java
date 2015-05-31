package knbit.events.bc.interest.domain.valueobjects.events.surveystarting;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy;
import org.joda.time.DateTime;

/**
 * Created by novy on 28.05.15.
 */
public class SurveyingInterestWithEndingDateStartedEventFactory
        implements SurveyingInterestStartedEventFactory {

    private final DateTime endingSurveyDate;

    public SurveyingInterestWithEndingDateStartedEventFactory(DateTime endingSurveyDate) {
        this.endingSurveyDate = endingSurveyDate;
    }

    @Override
    public SurveyingInterestStartedEvent newSurveyingInterestStartedEvent(EventId eventId, InterestPolicy thresholdPolicy) {
        return SurveyingInterestWithEndingDateStartedEvent.of(eventId, thresholdPolicy, endingSurveyDate);
    }
}
