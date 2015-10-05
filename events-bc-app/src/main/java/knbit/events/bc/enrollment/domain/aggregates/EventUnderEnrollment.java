package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.Maps;
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.entities.Term;
import knbit.events.bc.enrollment.domain.exceptions.EnrollmentExceptions;
import knbit.events.bc.enrollment.domain.exceptions.EventUnderEnrollmentExceptions;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

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
                                Collection<IdentifiedTerm> terms) {
        apply(
                EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, terms)
        );
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

    public void limitParticipants(TermId termId, ParticipantsLimit newLimit) {
        rejectOnNotExistingTerm(termId);

        final Term term = terms.get(termId);
        term.limitParticipants(newLimit);
    }

    public void enrollFor(TermId termId, MemberId memberId) {
        rejectOnNotExistingTerm(termId);
        rejectIfAlreadyEnrolledForAnyTerm(memberId);

        final Term term = terms.get(termId);
        term.enroll(memberId);

    }

    private void rejectIfAlreadyEnrolledForAnyTerm(MemberId memberId) {
        final boolean participantEnrolledForAnyTerm = terms.values()
                .stream()
                .anyMatch(term -> term.enrolled(memberId));

        if (participantEnrolledForAnyTerm) {
            throw new EnrollmentExceptions.AlreadyEnrolledForEvent(memberId, id);
        }
    }

    public void disenrollFrom(TermId termId, MemberId memberId) {
        rejectOnNotExistingTerm(termId);

        final Term term = terms.get(termId);
        term.disenroll(memberId);
    }
}
