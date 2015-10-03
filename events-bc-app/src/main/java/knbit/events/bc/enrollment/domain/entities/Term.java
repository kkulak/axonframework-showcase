package knbit.events.bc.enrollment.domain.entities;

import com.google.common.collect.Sets;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.common.domain.IdentifiedDomainEntity;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.exceptions.EnrollmentExceptions;
import knbit.events.bc.enrollment.domain.exceptions.EventUnderEnrollmentExceptions;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantLimit;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.TermEvent;
import knbit.events.bc.enrollment.domain.valueobjects.events.TermModifyingEvents;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Optional;
import java.util.Set;

/**
 * Created by novy on 03.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Term extends IdentifiedDomainEntity<TermId> {

    private EventId eventId;
    private EventDuration duration;
    private Capacity capacity;
    private Location location;

    private Lecturer lecturer;
    private ParticipantLimit participantLimit;

    private Set<ParticipantId> enrolledUsers = Sets.newHashSet();

    public Term(EventId eventId, TermId termId, EventDuration duration, Capacity capacity, Location location) {
        this.eventId = eventId;
        this.id = termId;
        this.duration = duration;
        this.capacity = capacity;
        this.location = location;
        this.participantLimit = ParticipantLimit.of(capacity);
    }


    public void assignLecturer(Lecturer lecturer) {
        apply(TermModifyingEvents.LecturerAssigned.of(eventId, id, lecturer));
    }

    @EventSourcingHandler
    private void on(TermModifyingEvents.LecturerAssigned event) {
        eventPossiblyMatchingCurrentTerm(event)
                .ifPresent(matchingEvent -> this.lecturer = matchingEvent.lecturer());
    }

    public void limitParticipants(ParticipantLimit newLimit) {
        checkIfLimitIsNotTooHigh(newLimit);
        checkIfLimitIsNotTooLowForCurrentParticipants(newLimit);

        apply(TermModifyingEvents.ParticipantLimitSet.of(eventId, id, newLimit));
    }

    private void checkIfLimitIsNotTooLowForCurrentParticipants(ParticipantLimit newLimit) {
        if (newLimit.value() < enrolledUsers.size()) {
            throw new EventUnderEnrollmentExceptions.ParticipantLimitTooLow(id, newLimit.value());
        }
    }

    private void checkIfLimitIsNotTooHigh(ParticipantLimit newLimit) {
        if (!newLimit.fitsCapacity(capacity)) {
            throw new EventUnderEnrollmentExceptions.ParticipantLimitTooHigh(id, newLimit.value());
        }
    }

    @EventSourcingHandler
    private void on(TermModifyingEvents.ParticipantLimitSet event) {
        eventPossiblyMatchingCurrentTerm(event)
                .ifPresent(matchingEvent -> this.participantLimit = matchingEvent.participantLimit());
    }

    public void enroll(ParticipantId participantId) {
        rejectIfParticipantLimitExceeded();

        apply(EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, id, participantId));
    }

    private void rejectIfParticipantLimitExceeded() {
        if (enrolledUsers.size() == participantLimit.value()) {
            throw new EnrollmentExceptions.EnrollmentLimitExceeded(participantLimit, id);
        }
    }

    private void rejectIfNotEnrolled(ParticipantId participantId) {
        if (!enrolled(participantId)) {
            throw new EnrollmentExceptions.NotYetEnrolled(participantId, id);
        }
    }

    @EventSourcingHandler
    private void on(EnrollmentEvents.ParticipantEnrolledForTerm event) {
        eventPossiblyMatchingCurrentTerm(event)
                .ifPresent(matchingEvent -> this.enrolledUsers.add(matchingEvent.participantId()));
    }


    public void disenroll(ParticipantId participantId) {
        rejectIfNotEnrolled(participantId);

        apply(EnrollmentEvents.ParticipantDisenrolledFromTerm.of(eventId, id, participantId));
    }

    @EventSourcingHandler
    private void on(EnrollmentEvents.ParticipantDisenrolledFromTerm event) {
        eventPossiblyMatchingCurrentTerm(event)
                .ifPresent(matchingEvent -> this.enrolledUsers.remove(matchingEvent.participantId()));
    }

    public boolean enrolled(ParticipantId participantId) {
        return enrolledUsers.contains(participantId);
    }

    private void invokeOnlyIfConcernedWith(TermEvent event, Runnable callback) {
        if (id.equals(event.termId())) {
            callback.run();
        }
    }

    private <T extends TermEvent> Optional<T> eventPossiblyMatchingCurrentTerm(T termEvent) {
        return Optional.of(termEvent)
                .filter(event -> id.equals(event.termId()));
    }
}
