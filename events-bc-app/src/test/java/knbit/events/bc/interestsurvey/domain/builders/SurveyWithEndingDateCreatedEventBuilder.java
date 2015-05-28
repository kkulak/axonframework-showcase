package knbit.events.bc.interestsurvey.domain.builders;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interestsurvey.domain.policies.InterestThresholdPolicy;
import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;
import knbit.events.bc.interestsurvey.domain.valueobjects.events.surveycreation.SurveyWithEndingDateCreatedEvent;
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
public class SurveyWithEndingDateCreatedEventBuilder {

    private SurveyId surveyId = SurveyId.of("surveyId");
    private EventId eventId = EventId.of("eventId");
    // todo: nope
    private InterestThresholdPolicy thresholdPolicy = null;
    private DateTime endingSurveyDate = DateTime.now();

    public SurveyWithEndingDateCreatedEvent build() {
        return new SurveyWithEndingDateCreatedEvent(surveyId, eventId, thresholdPolicy, endingSurveyDate);
    }

}
