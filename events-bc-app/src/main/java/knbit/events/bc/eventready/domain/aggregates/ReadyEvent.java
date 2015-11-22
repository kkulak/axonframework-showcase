package knbit.events.bc.eventready.domain.aggregates;

import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
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

    public void cancel() {
        rejectIfAlreadyCancelled();
        apply(ReadyEvents.Cancelled.of(id, attendees));
    }

    @EventSourcingHandler
    private void on(ReadyEvents.Cancelled event) {
        status = ReadyEventStatus.CANCELLED;
    }

    private void rejectIfAlreadyCancelled() {
        if (status == ReadyEventStatus.CANCELLED) {
            throw new EventReadyExceptions.AlreadyCancelled(id);
        }
    }
}
