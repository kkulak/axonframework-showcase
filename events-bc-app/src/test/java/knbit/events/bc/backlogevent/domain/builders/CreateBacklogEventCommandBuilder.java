package knbit.events.bc.backlogevent.domain.builders;

import knbit.events.bc.backlogevent.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.CreateBacklogEventCommand;
import knbit.events.bc.common.domain.enums.EventType;
import lombok.NoArgsConstructor;

/**
 * Created by novy on 07.05.15.
 */

@NoArgsConstructor(staticName = "newCreateBacklogEventCommand")
public class CreateBacklogEventCommandBuilder {

    private EventId eventId = EventId.of("id");
    private Name name = Name.of("name");
    private Description description = Description.of("description");
    private EventType eventType = EventType.LECTURE;
    private EventFrequency eventFrequency = EventFrequency.ONE_OFF;

    public CreateBacklogEventCommandBuilder eventId(EventId eventId) {
        this.eventId = eventId;
        return this;
    }

    public CreateBacklogEventCommandBuilder name(String name) {
        this.name = Name.of(name);
        return this;
    }

    public CreateBacklogEventCommandBuilder description(String description) {
        this.description = Description.of(description);
        return this;
    }

    public CreateBacklogEventCommandBuilder eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public CreateBacklogEventCommandBuilder eventFrequency(EventFrequency eventFrequency) {
        this.eventFrequency = eventFrequency;
        return this;
    }

    public CreateBacklogEventCommand build() {
        return new CreateBacklogEventCommand(eventId, EventDetails.of(name, description, eventType, eventFrequency));
    }
}
