package knbit.events.bc.interest.survey.domain.builders;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import knbit.events.bc.interest.survey.domain.policies.InterestThresholdTurnedOffPolicy;
import knbit.events.bc.interest.survey.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation.SurveyCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class SurveyCreatedEventBuilder {

    private SurveyId surveyId = SurveyId.of("surveyId");
    private EventId eventId = EventId.of("eventId");
    private InterestPolicy thresholdPolicy = new InterestThresholdTurnedOffPolicy();

    public SurveyCreatedEvent build() {
        return new SurveyCreatedEvent(surveyId, eventId, thresholdPolicy);
    }
}