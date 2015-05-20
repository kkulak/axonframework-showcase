package knbit.events.bc.event.domain.builders;

import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.event.domain.enums.EventState;
import knbit.events.bc.event.domain.valueobjects.Description;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.valueobjects.Name;
import knbit.events.bc.event.domain.valueobjects.events.CyclicEventCreated;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true)
@NoArgsConstructor(staticName = "instance")
public class CyclicEventCreatedBuilder {
    private EventId eventId = EventId.of("id");
    private EventType type = EventType.LECTURE;
    private Name name = Name.of("name");
    private Description description = Description.of("description");
    private EventState state = EventState.CREATED;

    public CyclicEventCreated build() {
        return CyclicEventCreated.of(eventId, type, name, description, state);
    }

}
