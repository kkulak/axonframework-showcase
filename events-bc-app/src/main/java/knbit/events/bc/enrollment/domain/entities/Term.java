package knbit.events.bc.enrollment.domain.entities;

import com.google.common.collect.Lists;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.common.domain.IdentifiedDomainEntity;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.exceptions.EnrollmentExceptions;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.TermEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by novy on 03.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Term extends IdentifiedDomainEntity<TermId> {

    private EventId eventId;
    private EventDuration duration;
    private Capacity capacity;
    private Location location;

    private Collection<Lecturer> lecturers;

    private ParticipantsLimit participantsLimit;

    private List<MemberId> enrolledUsers = Lists.newLinkedList();

    public Term(EventId eventId, TermId termId, EventDuration duration,
                Capacity capacity, Location location,
                ParticipantsLimit participantsLimit, Collection<Lecturer> lecturers) {
        checkArgument(lecturers != null && !lecturers.isEmpty(), "Lecturers list is required");

        this.eventId = eventId;
        this.id = termId;
        this.duration = duration;
        this.capacity = capacity;
        this.location = location;
        this.participantsLimit = participantsLimit;
        this.lecturers = lecturers;
    }

    public void enroll(MemberId memberId) {
        rejectIfParticipantLimitExceeded();

        apply(EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, id, memberId));
    }

    private void rejectIfParticipantLimitExceeded() {
        if (enrolledUsers.size() == participantsLimit.value()) {
            throw new EnrollmentExceptions.EnrollmentLimitExceeded(participantsLimit, id);
        }
    }

    private void rejectIfNotEnrolled(MemberId memberId) {
        if (!enrolled(memberId)) {
            throw new EnrollmentExceptions.NotYetEnrolled(memberId, id);
        }
    }

    @EventSourcingHandler
    private void on(EnrollmentEvents.ParticipantEnrolledForTerm event) {
        eventPossiblyMatchingCurrentTerm(event)
                .ifPresent(matchingEvent -> this.enrolledUsers.add(matchingEvent.memberId()));
    }


    public void disenroll(MemberId memberId) {
        rejectIfNotEnrolled(memberId);

        apply(EnrollmentEvents.ParticipantDisenrolledFromTerm.of(eventId, id, memberId));
    }

    @EventSourcingHandler
    private void on(EnrollmentEvents.ParticipantDisenrolledFromTerm event) {
        eventPossiblyMatchingCurrentTerm(event)
                .ifPresent(matchingEvent -> this.enrolledUsers.remove(matchingEvent.memberId()));
    }

    public boolean enrolled(MemberId memberId) {
        return enrolledUsers.contains(memberId);
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

    public IdentifiedTermWithAttendees asValueObject() {
        return IdentifiedTermWithAttendees.of(
                id, duration, participantsLimit, location, lecturers, attendees()
        );
    }

    private Collection<Attendee> attendees() {
        return enrolledUsers.stream()
                .map(Attendee::of)
                .collect(Collectors.toList());
    }
}
