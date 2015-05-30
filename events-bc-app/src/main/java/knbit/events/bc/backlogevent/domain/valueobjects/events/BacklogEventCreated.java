package knbit.events.bc.backlogevent.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.readmodel.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor(staticName = "of")
@Getter
@Accessors(fluent = true)
public class BacklogEventCreated {

    private final EventId eventId;
    private final EventDetails eventDetails;

    public EventStatus status() {
        return EventStatus.BACKLOG;
    }

}
