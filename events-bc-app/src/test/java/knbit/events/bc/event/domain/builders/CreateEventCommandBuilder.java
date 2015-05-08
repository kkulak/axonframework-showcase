package knbit.events.bc.event.domain.builders;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.valueobjects.commands.CreateEventCommand;
import knbit.events.bc.common.domain.enums.EventType;
import lombok.NoArgsConstructor;

/**
 * Created by novy on 07.05.15.
 */

@NoArgsConstructor(staticName = "newCreateEventCommand")
public class CreateEventCommandBuilder {

    private EventId eventId = EventId.of("id");
    private String name = "name";
    private String description = "description";
    private EventType eventType = EventType.LECTURE;
    private EventFrequency eventFrequency = EventFrequency.ONE_OFF;

    public CreateEventCommandBuilder eventId(EventId eventId) {
        this.eventId = eventId;
        return this;
    }

    public CreateEventCommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CreateEventCommandBuilder description(String description) {
        this.description = description;
        return this;
    }

    public CreateEventCommandBuilder eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public CreateEventCommandBuilder eventFrequency(EventFrequency eventFrequency) {
        this.eventFrequency = eventFrequency;
        return this;
    }

    public CreateEventCommand build() {
        return new CreateEventCommand(eventId, name, description, eventType, eventFrequency);
    }
}
