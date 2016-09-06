package knbit.events.bc.interest.builders;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyStartingEvents;
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
    private EventDetails eventDetails = EventDetailsBuilder.defaultEventDetails();
    private InterestPolicy thresholdPolicy = new InterestThresholdTurnedOffPolicy();
    private DateTime endingSurveyDate = DateTime.now();

    public SurveyStartingEvents.StartedWithEndingDate build() {
        return SurveyStartingEvents.StartedWithEndingDate.of(eventId, eventDetails, thresholdPolicy, endingSurveyDate);
    }

}
