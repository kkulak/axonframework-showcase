package knbit.events.bc.event.domain.valueobjects.commands;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.enums.EventType;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 07.05.15.
 */
@Value
@Accessors(fluent = true)
public class CreateEventCommand {

    private final EventId eventId;
    private final String name;
    private final String description;
    private final EventType eventType;
    private final EventFrequency eventFrequency;

}
