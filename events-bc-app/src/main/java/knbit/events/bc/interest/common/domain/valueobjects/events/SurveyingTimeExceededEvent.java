package knbit.events.bc.interest.common.domain.valueobjects.events;

import knbit.events.bc.event.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value
public class SurveyingTimeExceededEvent {

    private final EventId eventId;
    private final DateTime surveyingEndDate;
}
