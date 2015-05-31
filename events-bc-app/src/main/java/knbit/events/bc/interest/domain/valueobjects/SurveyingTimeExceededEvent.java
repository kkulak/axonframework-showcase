package knbit.events.bc.interest.domain.valueobjects;

import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class SurveyingTimeExceededEvent {

    private final EventId eventId;
    private final DateTime surveyingEndDate;
}
