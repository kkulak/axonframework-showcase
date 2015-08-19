package knbit.events.bc.choosingterm.domain.aggregates;

import knbit.events.bc.choosingterm.domain.enums.UnderChoosingTermEventState;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventCreated;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import static knbit.events.bc.choosingterm.domain.enums.UnderChoosingTermEventState.CREATED;


/**
 * Created by novy on 16.08.15.
 */
public class UnderChoosingTermEvent extends IdentifiedDomainAggregateRoot<EventId> {

    private EventDetails eventDetails;

    private UnderChoosingTermEventState state;

    private UnderChoosingTermEvent() {

    }

    public UnderChoosingTermEvent(EventId eventId, EventDetails eventDetails) {
        apply(UnderChoosingTermEventCreated.of(eventId, eventDetails));
    }

    @EventSourcingHandler
    private void on(UnderChoosingTermEventCreated event) {
        this.id = event.eventId();
        this.eventDetails = event.eventDetails();

        this.state = CREATED;
    }
}
