package knbit.events.bc.backlogevent.domain.aggregates;

import com.google.common.base.Preconditions;
import knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventStatus;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitionEvents;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import static knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventStatus.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BacklogEvent extends IdentifiedDomainAggregateRoot<EventId> {
    private EventDetails eventDetails;
    private BacklogEventStatus status;

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

    public void changeDetails(EventDetails newEventDetails) {
        checkIfNotCancelledOrTransited();

        apply(BacklogEventEvents.EventDetailsChanged.of(id, eventDetails, newEventDetails));
    }

    @EventSourcingHandler
    private void on(BacklogEventEvents.EventDetailsChanged event) {
        this.eventDetails = event.newDetails();
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
        Preconditions.checkState(status != CANCELLED, "Already cancelled");
        Preconditions.checkState(status != TRANSITED, "Already transited");
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
