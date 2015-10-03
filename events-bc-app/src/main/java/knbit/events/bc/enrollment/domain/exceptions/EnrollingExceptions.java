package knbit.events.bc.enrollment.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.Participant.ParticipantId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantLimit;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;

/**
 * Created by novy on 03.10.15.
 */
public interface EnrollingExceptions {

    class AlreadyEnrolledForTerm extends DomainException {
        private static final String MESSAGE_PATTERN = "Participant %s already enrolled for term %s";

        public AlreadyEnrolledForTerm(ParticipantId participantId, TermId termId) {
            super(String.format(MESSAGE_PATTERN, participantId, termId));
        }
    }

    class AlreadyEnrolledForEvent extends DomainException {
        private static final String MESSAGE_PATTERN = "Participant %s has already chosen another term (eventId = %s)";

        public AlreadyEnrolledForEvent(ParticipantId participantId, EventId eventId) {
            super(String.format(MESSAGE_PATTERN, participantId, eventId));
        }
    }

    class NotYetEnrolled extends DomainException {
        private static final String MESSAGE_PATTERN = "Participant %s not yet enrolled for term %s";

        public NotYetEnrolled(ParticipantId participantId, TermId termId) {
            super(String.format(MESSAGE_PATTERN, participantId, termId));
        }
    }

    class EnrollmentLimitExceeded extends DomainException {
        private static final String MESSAGE_PATTERN = "Could not enroll, limit exceeded (%s) for term %s";

        public EnrollmentLimitExceeded(ParticipantLimit limit, TermId termId) {
            super(String.format(MESSAGE_PATTERN, limit, termId));
        }
    }
}
