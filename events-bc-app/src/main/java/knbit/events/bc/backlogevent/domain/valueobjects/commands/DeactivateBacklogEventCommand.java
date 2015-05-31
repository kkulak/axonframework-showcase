package knbit.events.bc.backlogevent.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class DeactivateBacklogEventCommand {

    private final EventId eventId;

}
