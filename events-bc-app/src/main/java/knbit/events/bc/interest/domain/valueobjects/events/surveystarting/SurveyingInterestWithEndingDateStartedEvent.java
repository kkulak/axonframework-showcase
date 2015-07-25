package knbit.events.bc.interest.domain.valueobjects.events.surveystarting;


import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy;
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
public class SurveyingInterestWithEndingDateStartedEvent extends SurveyingInterestStartedEvent {

    private final DateTime endingSurveyDate;

    public static SurveyingInterestWithEndingDateStartedEvent of(EventId eventId,
                                                                 InterestPolicy interestPolicy,
                                                                 DateTime endingSurveyDate) {

        return new SurveyingInterestWithEndingDateStartedEvent(eventId, interestPolicy, endingSurveyDate);
    }

    private SurveyingInterestWithEndingDateStartedEvent(EventId eventId,
                                                        InterestPolicy interestPolicy,
                                                        DateTime endingSurveyDate) {
        super(eventId, interestPolicy);
        this.endingSurveyDate = endingSurveyDate;
    }
}
