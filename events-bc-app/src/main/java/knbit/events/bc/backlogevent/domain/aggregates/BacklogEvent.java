package knbit.events.bc.backlogevent.domain.aggregates;

import com.google.common.base.Preconditions;
import knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventState;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventCreated;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventDeactivated;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.aggregates.InterestAwareEvent;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import static knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventState.ACTIVE;

public class BacklogEvent extends IdentifiedDomainAggregateRoot<EventId> {
    private EventDetails eventDetails;
    private BacklogEventState state;

    private BacklogEvent() {
    }

    public BacklogEvent(EventId eventId, EventDetails eventDetails) {
        apply(
                BacklogEventCreated.of(eventId, eventDetails, ACTIVE)
        );
    }

    public void deactivate() {
        Preconditions.checkState(state == ACTIVE, "Trying to deactivate already inactive BacklogEvent");

        apply(
                BacklogEventDeactivated.of(id, eventDetails, BacklogEventState.INACTIVE)
        );
    }

    @EventSourcingHandler
    private void on(BacklogEventCreated event) {
        this.id = event.eventId();
        this.eventDetails = event.eventDetails();
        this.state = event.state();
    }

    @EventSourcingHandler
    private void on(BacklogEventDeactivated event) {
        this.state = event.state();
    }

}
