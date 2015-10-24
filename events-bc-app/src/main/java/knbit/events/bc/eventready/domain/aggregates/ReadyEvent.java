package knbit.events.bc.eventready.domain.aggregates;

import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;

/**
 * Created by novy on 24.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadyEvent extends IdentifiedDomainAggregateRoot<EventId> {

    private EventDetails eventDetails;
    private Collection<IdentifiedTermWithAttendees> terms;

    public ReadyEvent(EventId eventId,
                      EventDetails eventDetails,
                      Collection<IdentifiedTermWithAttendees> terms) {

        apply(ReadyEvents.Created.of(eventId, eventDetails, terms));
    }

    @EventSourcingHandler
    private void on(ReadyEvents.Created event) {
        id = event.eventId();
        eventDetails = event.eventDetails();
        terms = event.terms();
    }
}
