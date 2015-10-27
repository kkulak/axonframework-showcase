package knbit.events.bc.backlogevent.domain.builders;

import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Name;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true)
@NoArgsConstructor(staticName = "instance")
public class BacklogEventCreatedBuilder {
    private EventId eventId = EventId.of("id");
    private EventType type = EventType.LECTURE;
    private Name name = Name.of("name");
    private Description description = Description.of("description");

    public BacklogEventEvents.Created build() {
        return BacklogEventEvents.Created.of(eventId, EventDetails.of(name, description, type));
    }

}
