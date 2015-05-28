package knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import knbit.events.bc.interest.survey.domain.valueobjects.SurveyId;
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
