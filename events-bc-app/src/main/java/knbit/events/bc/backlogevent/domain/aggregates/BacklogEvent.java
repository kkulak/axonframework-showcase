package knbit.events.bc.backlogevent.domain.aggregates;

import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.backlogevent.domain.valueobjects.EventId;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventCreated;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class BacklogEvent extends IdentifiedDomainAggregateRoot<EventId> {
    private EventDetails eventDetails;

    private BacklogEvent() { }

    public BacklogEvent(EventId eventId, EventDetails eventDetails) {
        apply(
                BacklogEventCreated.of(eventId, eventDetails)
        );
    }

    @EventSourcingHandler
    private void on(BacklogEventCreated event) {
        this.id = event.eventId();
        this.eventDetails = event.eventDetails();
    }

}
