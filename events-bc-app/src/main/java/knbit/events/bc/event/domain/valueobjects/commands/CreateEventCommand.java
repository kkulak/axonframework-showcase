package knbit.events.bc.event.domain.valueobjects.commands;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.eventproposal.domain.enums.EventType;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 07.05.15.
 */
@Value
@Accessors(fluent = true)
public class CreateEventCommand {

    // todo: MOOOAAR props
    private final EventId eventId;
    private final String name;
    private final String description;
    private final EventType eventType;
}
