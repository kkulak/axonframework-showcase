package knbit.events.bc.interest.domain.valueobjects.events.surveycreation;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import org.joda.time.DateTime;

/**
 * Created by novy on 28.05.15.
 */
public class EndingDateAwareSurveyCreatedEventFactory implements SurveyCreatedEventFactory {

    private final DateTime endingSurveyDate;

    public EndingDateAwareSurveyCreatedEventFactory(DateTime endingSurveyDate) {
        this.endingSurveyDate = endingSurveyDate;
    }

    @Override
    public SurveyCreatedEvent newSurveyCreatedEvent(SurveyId surveyId, EventId eventId, InterestPolicy thresholdPolicy) {
        return new SurveyWithEndingDateCreatedEvent(surveyId, eventId, thresholdPolicy, endingSurveyDate);
    }
}
