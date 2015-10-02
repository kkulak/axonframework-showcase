package knbit.events.bc.enrollment.domain.aggregates;

import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.EventUnderEnrollmentEvents;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;

/**
 * Created by novy on 02.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventUnderEnrollment extends IdentifiedDomainAggregateRoot<EventId> {

    private EventDetails eventDetails;

    public EventUnderEnrollment(EventId eventId, EventDetails eventDetails, Collection<Term> terms) {
        apply(
                EventUnderEnrollmentEvents.Created.of(eventId, eventDetails)
        );
    }

    @EventSourcingHandler
    private void on(EventUnderEnrollmentEvents.Created event) {
        id = event.eventId();
        eventDetails = event.eventDetails();
    }
}
