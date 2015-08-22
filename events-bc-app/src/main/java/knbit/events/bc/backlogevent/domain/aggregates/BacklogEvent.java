package knbit.events.bc.backlogevent.domain.aggregates;

import com.google.common.base.Preconditions;
import knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventState;
import knbit.events.bc.backlogevent.domain.valueobjects.events.*;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import static knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventState.ACTIVE;
import static knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventState.INACTIVE;

public class BacklogEvent extends IdentifiedDomainAggregateRoot<EventId> {
    private EventDetails eventDetails;
    private BacklogEventState state;

    private BacklogEvent() {
    }

    public BacklogEvent(EventId eventId, EventDetails eventDetails) {
        apply(BacklogEventEvents.Created.of(eventId, eventDetails));
    }

    public void transitToSurveyInterestAwareEvent() {
        transitToAnotherState(
                BacklogEventTransitionEvents.TransitedToInterestAware.of(id, eventDetails)
        );
    }

    public void transitToUnderChoosingTermEvent() {
        transitToAnotherState(
                BacklogEventTransitionEvents.TransitedToUnderChoosingTerm.of(id, eventDetails)
        );
    }

    private void transitToAnotherState(BacklogEventTransitionEvents.TransitedToAnotherState transitionEvent) {
        Preconditions.checkState(state == ACTIVE, "Cannot transit inactive BacklogEvent");

        apply(transitionEvent);
    }

    @EventSourcingHandler
    private void on(BacklogEventEvents.Created event) {
        this.id = event.eventId();
        this.eventDetails = event.eventDetails();
        this.state = ACTIVE;
    }

    @EventSourcingHandler
    private void on(BacklogEventTransitionEvents.TransitedToAnotherState event) {
        this.state = INACTIVE;
    }

}
