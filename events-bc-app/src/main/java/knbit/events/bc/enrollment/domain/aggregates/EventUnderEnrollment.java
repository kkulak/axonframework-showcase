package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.Maps;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.EventUnderEnrollmentExceptions;
import knbit.events.bc.enrollment.domain.entities.Term;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantLimit;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.IdentifiedTerm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by novy on 02.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventUnderEnrollment extends IdentifiedDomainAggregateRoot<EventId> {

    private EventDetails eventDetails;

    @EventSourcedMember
    private Map<TermId, Term> terms = Maps.newHashMap();

    public EventUnderEnrollment(EventId eventId,
                                EventDetails eventDetails,
                                Collection<knbit.events.bc.choosingterm.domain.valuobjects.Term> terms) {
        apply(
                EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, assignTermIdsTo(terms))
        );
    }

    private Collection<IdentifiedTerm> assignTermIdsTo(
            Collection<knbit.events.bc.choosingterm.domain.valuobjects.Term> terms) {

        final Function<knbit.events.bc.choosingterm.domain.valuobjects.Term, IdentifiedTerm> assignRandomTermId =
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

        final Consumer<IdentifiedTerm> createAndSaveTermEntity = identifiedTerm -> terms.put(
                identifiedTerm.termId(),
                new Term(
                        this.id,
                        identifiedTerm.termId(),
                        identifiedTerm.duration(),
                        identifiedTerm.capacity(),
                        identifiedTerm.location()
                )
        );

        event.terms()
                .stream()
                .forEach(createAndSaveTermEntity);
    }

    public void assignLecturer(TermId termId, Lecturer lecturer) {
        rejectOnNotExistingTerm(termId);

        final Term term = terms.get(termId);
        term.assignLecturer(lecturer);
    }


    private void rejectOnNotExistingTerm(TermId termId) {
        if (!terms.containsKey(termId)) {
            throw new EventUnderEnrollmentExceptions.NoSuchTermException(id, termId);
        }
    }

    public void limitParticipants(TermId termId, ParticipantLimit newLimit) {
        rejectOnNotExistingTerm(termId);

        final Term term = terms.get(termId);
        term.limitParticipants(newLimit);
    }
}
