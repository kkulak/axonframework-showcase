package knbit.events.bc.backlogevent.domain.valueobjects.events;

import knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventState;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class BacklogEventDeactivated {

    private final EventId eventId;
    private final EventDetails eventDetails;
    private final BacklogEventState state;

}
