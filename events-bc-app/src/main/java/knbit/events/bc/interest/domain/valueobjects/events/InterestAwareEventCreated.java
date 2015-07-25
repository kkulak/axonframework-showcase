package knbit.events.bc.interest.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.readmodel.EventStatus;
import knbit.events.bc.common.readmodel.EventStatusAware;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 30.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class InterestAwareEventCreated implements EventStatusAware {

    private final EventId eventId;
    private final EventDetails eventDetails;

    @Override
    public EventStatus status() {
        return EventStatus.SURVEY_INTEREST;
    }
}
