package knbit.events.bc.backlogevent.domain.aggregates;

import com.google.common.base.Preconditions;
import knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventStatus;
import knbit.events.bc.backlogevent.domain.valueobjects.events.*;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import static knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventStatus.ACTIVE;
import static knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventStatus.CANCELLED;
import static knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventStatus.TRANSITED;

public class BacklogEvent extends IdentifiedDomainAggregateRoot<EventId> {
    private EventDetails eventDetails;
    private BacklogEventStatus status;

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

    public void cancel() {
        checkIfNotCancelledOrTransited();

        apply(BacklogEventEvents.Cancelled.of(id));
    }

    private void transitToAnotherState(BacklogEventTransitionEvents.TransitedToAnotherState transitionEvent) {
        checkIfNotCancelledOrTransited();

        apply(transitionEvent);
    }

    private void checkIfNotCancelledOrTransited() {
        Preconditions.checkState(status != CANCELLED , "Cannot transit cancelled BacklogEvent");
        Preconditions.checkState(status != TRANSITED, "Cannot transit already transited BacklogEvent");
    }

    @EventSourcingHandler
    private void on(BacklogEventEvents.Created event) {
        this.id = event.eventId();
        this.eventDetails = event.eventDetails();
        this.status = ACTIVE;
    }

    @EventSourcingHandler
    private void on(BacklogEventTransitionEvents.TransitedToAnotherState event) {
        this.status = TRANSITED;
    }

    @EventSourcingHandler
    private void on(BacklogEventEvents.Cancelled event) {
        this.status = CANCELLED;
    }
}
