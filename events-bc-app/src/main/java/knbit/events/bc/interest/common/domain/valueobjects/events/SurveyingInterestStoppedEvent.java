package knbit.events.bc.interest.common.domain.valueobjects.events;

import knbit.events.bc.backlogevent.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value
public class SurveyingInterestStoppedEvent {

    private final EventId eventId;
}
