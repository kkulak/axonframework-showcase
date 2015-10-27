package knbit.events.bc.backlogevent.domain.builders;

import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Name;
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

    public BacklogEventCommands.Create build() {
        return BacklogEventCommands.Create.of(eventId, EventDetails.of(name, description, eventType));
    }
}
