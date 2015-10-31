package knbit.events.bc.backlogevent.domain.builders;

import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 07.05.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newCreateBacklogEventCommand")
public class CreateBacklogEventCommandBuilder {

    private EventId eventId = EventId.of("id");
    private Name name = Name.of("name");
    private Description description = Description.of("description");
    private EventType eventType = EventType.LECTURE;
    private URL imageUrl = URL.of("https://www.google.pl/");
    private Section section = Section.of("0", "Idea Factory");

    public CreateBacklogEventCommandBuilder eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public BacklogEventCommands.Create build() {
        return BacklogEventCommands.Create.of(eventId, EventDetails.of(name, description, eventType, imageUrl, section));
    }
}
