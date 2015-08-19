package knbit.events.bc.choosingterm.domain.valuobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.readmodel.EventStatus;
import knbit.events.bc.common.readmodel.EventStatusAware;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 16.08.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class UnderChoosingTermEventCreated implements EventStatusAware {

    private final EventId eventId;
    private final EventDetails eventDetails;

    @Override
    public EventStatus status() {
        return EventStatus.CHOOSING_TERM;
    }
}
