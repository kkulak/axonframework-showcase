package knbit.events.bc.interest.builders;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyingInterestWithEndingDateStartedEvent;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestThresholdTurnedOffPolicy;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

/**
 * Created by novy on 28.05.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class SurveyingInterestWithEndingDateStartedEventBuilder {

    private EventId eventId = EventId.of("eventId");
    private InterestPolicy thresholdPolicy = new InterestThresholdTurnedOffPolicy();
    private DateTime endingSurveyDate = DateTime.now();

    public SurveyingInterestWithEndingDateStartedEvent build() {
        return SurveyingInterestWithEndingDateStartedEvent.of(eventId, thresholdPolicy, endingSurveyDate);
    }

}
