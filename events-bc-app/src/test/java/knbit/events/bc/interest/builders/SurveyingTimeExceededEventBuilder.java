package knbit.events.bc.interest.builders;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.common.domain.valueobjects.events.SurveyingTimeExceededEvent;
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
public class SurveyingTimeExceededEventBuilder {

    private EventId eventId = EventId.of("eventId");
    private DateTime surveyingEndTime = DateTime.now();

    public SurveyingTimeExceededEvent build() {
        return SurveyingTimeExceededEvent.of(eventId, surveyingEndTime);
    }

}
