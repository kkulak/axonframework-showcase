package knbit.events.bc.enrollment.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;

/**
 * Created by novy on 03.10.15.
 */
public interface EnrollmentExceptions {

    class AlreadyEnrolledForEvent extends DomainException {
        private static final String MESSAGE_PATTERN = "Member %s is already enrolled for this event (%s)";

        public AlreadyEnrolledForEvent(MemberId memberId, EventId eventId) {
            super(String.format(MESSAGE_PATTERN, memberId, eventId));
        }
    }

    class NotYetEnrolled extends DomainException {
        private static final String MESSAGE_PATTERN = "Member %s not yet enrolled for term %s";

        public NotYetEnrolled(MemberId memberId, TermId termId) {
            super(String.format(MESSAGE_PATTERN, memberId, termId));
        }
    }

    class EnrollmentLimitExceeded extends DomainException {
        private static final String MESSAGE_PATTERN = "Could not enroll, limit exceeded (%s) for term %s";

        public EnrollmentLimitExceeded(ParticipantsLimit limit, TermId termId) {
            super(String.format(MESSAGE_PATTERN, limit, termId));
        }
    }
}
