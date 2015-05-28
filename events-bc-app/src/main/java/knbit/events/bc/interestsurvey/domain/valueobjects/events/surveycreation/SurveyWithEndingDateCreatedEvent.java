package knbit.events.bc.interestsurvey.domain.valueobjects.events.surveycreation;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interestsurvey.domain.policies.InterestThresholdPolicy;
import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;
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
                                            InterestThresholdPolicy interestThresholdPolicy,
                                            DateTime endingSurveyDate) {
        super(surveyId, eventId, interestThresholdPolicy);
        this.endingSurveyDate = endingSurveyDate;
    }
}
