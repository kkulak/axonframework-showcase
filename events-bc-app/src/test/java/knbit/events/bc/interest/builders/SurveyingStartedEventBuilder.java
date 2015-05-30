package knbit.events.bc.interest.builders;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyingStartedEvent;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import knbit.events.bc.interest.survey.domain.policies.InterestThresholdTurnedOffPolicy;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 30.05.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class SurveyingStartedEventBuilder {

    private EventId eventId = EventId.of("eventId");
    private InterestPolicy interestPolicy = new InterestThresholdTurnedOffPolicy();

    public SurveyingStartedEvent build() {
        return SurveyingStartedEvent.of(eventId, interestPolicy);
    }
}
