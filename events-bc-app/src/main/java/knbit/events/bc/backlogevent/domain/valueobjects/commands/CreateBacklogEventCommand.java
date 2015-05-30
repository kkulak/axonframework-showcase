package knbit.events.bc.backlogevent.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.backlogevent.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 07.05.15.
 */
@Value
@Accessors(fluent = true)
public class CreateBacklogEventCommand {

    private final EventId eventId;
    private final EventDetails eventDetails;

}
