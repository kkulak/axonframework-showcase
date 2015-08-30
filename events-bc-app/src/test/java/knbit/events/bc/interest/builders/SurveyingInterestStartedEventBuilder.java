package knbit.events.bc.interest.builders;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyStartingEvents;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestThresholdTurnedOffPolicy;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class SurveyingInterestStartedEventBuilder {

    private EventId eventId = EventId.of("eventId");
    private InterestPolicy thresholdPolicy = new InterestThresholdTurnedOffPolicy();

    public SurveyStartingEvents.Started build() {
        return SurveyStartingEvents.Started.of(eventId, thresholdPolicy);
    }
}