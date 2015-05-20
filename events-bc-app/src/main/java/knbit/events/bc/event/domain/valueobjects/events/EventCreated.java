package knbit.events.bc.event.domain.valueobjects.events;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.readmodel.EventStatus;
import knbit.events.bc.event.domain.valueobjects.Description;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.enums.EventState;
import knbit.events.bc.event.domain.valueobjects.Name;
import knbit.events.bc.common.domain.enums.EventType;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public abstract class EventCreated {

    private final EventId eventId;
    private final EventType eventType;
    private final Name name;
    private final Description description;
    private final EventState eventState;
    private final EventFrequency eventFrequency;

    public EventStatus status() {
        return EventStatus.BACKLOG;
    }

}
