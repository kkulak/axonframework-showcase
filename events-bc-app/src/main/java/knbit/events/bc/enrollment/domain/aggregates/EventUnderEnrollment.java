package knbit.events.bc.enrollment.domain.aggregates;

import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.IdentifiedTerm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by novy on 02.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventUnderEnrollment extends IdentifiedDomainAggregateRoot<EventId> {

    private EventDetails eventDetails;

    public EventUnderEnrollment(EventId eventId, EventDetails eventDetails, Collection<Term> terms) {
        apply(
                EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, assignTermIdsTo(terms))
        );
    }

    private Collection<IdentifiedTerm> assignTermIdsTo(Collection<Term> terms) {
        final Function<Term, IdentifiedTerm> assignRandomTermId =
                term -> IdentifiedTerm.of(new TermId(), term);

        return terms
                .stream()
                .map(assignRandomTermId)
                .collect(Collectors.toList());
    }

    @EventSourcingHandler
    private void on(EventUnderEnrollmentEvents.Created event) {
        id = event.eventId();
        eventDetails = event.eventDetails();
    }
}
