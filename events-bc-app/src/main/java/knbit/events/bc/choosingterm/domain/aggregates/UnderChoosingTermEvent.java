package knbit.events.bc.choosingterm.domain.aggregates;

import com.google.common.collect.Sets;
import knbit.events.bc.choosingterm.domain.enums.UnderChoosingTermEventState;
import knbit.events.bc.choosingterm.domain.exceptions.CannotAddOverlappingTermException;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermAddedEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventCreated;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;

import static knbit.events.bc.choosingterm.domain.enums.UnderChoosingTermEventState.CREATED;


/**
 * Created by novy on 16.08.15.
 */
public class UnderChoosingTermEvent extends IdentifiedDomainAggregateRoot<EventId> {

    private EventDetails eventDetails;

    private Collection<Term> terms = Sets.newHashSet();
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

    public void addTerm(Term newTerm) {
        if (newTermOverlaps(newTerm)) {
            throw new CannotAddOverlappingTermException(id, newTerm);
        }

        apply(TermAddedEvent.of(id, newTerm));
    }

    private boolean newTermOverlaps(Term newTerm) {
        return terms
                .stream()
                .anyMatch(term -> term.overlaps(newTerm));
    }

    @EventSourcingHandler
    private void on(TermAddedEvent event) {
        terms.add(event.term());
    }
}
