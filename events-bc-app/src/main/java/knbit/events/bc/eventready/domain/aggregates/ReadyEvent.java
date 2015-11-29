package knbit.events.bc.eventready.domain.aggregates;

import com.google.common.collect.ImmutableMap;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.eventready.domain.EventReadyExceptions;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Supplier;

import static knbit.events.bc.eventready.domain.aggregates.ReadyEventStatus.CANCELLED;
import static knbit.events.bc.eventready.domain.aggregates.ReadyEventStatus.TOOK_PLACE;

/**
 * Created by novy on 24.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadyEvent extends IdentifiedDomainAggregateRoot<ReadyEventId> {

    private EventId correlationId;
    private EventReadyDetails eventDetails;
    private Collection<Attendee> attendees;
    private ReadyEventStatus status;

    public ReadyEvent(ReadyEventId eventId,
                      EventId correlationId,
                      EventReadyDetails eventDetails,
                      Collection<Attendee> attendees) {

        apply(ReadyEvents.Created.of(eventId, correlationId, eventDetails, attendees));
    }

    @EventSourcingHandler
    private void on(ReadyEvents.Created event) {
        id = event.readyEventId();
        correlationId = event.correlationId();
        eventDetails = event.eventDetails();
        attendees = event.attendees();
    }

    public void changeDetails(EventReadyDetails newDetails) {
        rejectIfAlreadyCancelledOrTookPlace();
        apply(ReadyEvents.DetailsChanged.of(id, eventDetails, newDetails));
    }

    @EventSourcingHandler
    private void on(ReadyEvents.DetailsChanged event) {
        eventDetails = event.newDetails();
    }

    public void markItTookPlace() {
        rejectIfAlreadyCancelledOrTookPlace();
        apply(ReadyEvents.TookPlace.of(id, eventDetails, attendees));
    }

    @EventSourcingHandler
    private void on(ReadyEvents.TookPlace event) {
        status = TOOK_PLACE;
    }

    public void cancel() {
        rejectIfAlreadyCancelledOrTookPlace();
        apply(ReadyEvents.Cancelled.of(id, attendees));
    }

    @EventSourcingHandler
    private void on(ReadyEvents.Cancelled event) {
        status = CANCELLED;
    }

    private void rejectIfAlreadyCancelledOrTookPlace() {
        final Map<ReadyEventStatus, Supplier<DomainException>> exceptionForStatus = ImmutableMap.of(
                CANCELLED, () -> new EventReadyExceptions.AlreadyCancelled(id),
                TOOK_PLACE, () -> new EventReadyExceptions.AlreadyMarkedItTookPlace(id)
        );

        if (EnumSet.of(CANCELLED, TOOK_PLACE).contains(status)) {
            throw exceptionForStatus.get(status).get();
        }
    }
}
