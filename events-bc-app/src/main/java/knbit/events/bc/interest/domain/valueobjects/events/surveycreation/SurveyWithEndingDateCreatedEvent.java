package knbit.events.bc.interest.domain.valueobjects.events.surveycreation;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SurveyWithEndingDateCreatedEvent extends SurveyCreatedEvent {

    private final DateTime endingSurveyDate;

    public SurveyWithEndingDateCreatedEvent(SurveyId surveyId, EventId eventId,
                                            InterestPolicy interestPolicy,
                                            DateTime endingSurveyDate) {
        super(surveyId, eventId, interestPolicy);
        this.endingSurveyDate = endingSurveyDate;
    }
}
