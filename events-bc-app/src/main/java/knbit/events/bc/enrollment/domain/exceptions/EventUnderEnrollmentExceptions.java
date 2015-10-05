package knbit.events.bc.enrollment.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;

/**
 * Created by novy on 03.10.15.
 */
public interface EventUnderEnrollmentExceptions {

    class NoSuchTermException extends DomainException {

        private static final String MESSAGE_PATTERN = "There's no such term (%s) assigned to event %s";

        public NoSuchTermException(EventId eventId, TermId termId) {
            super(String.format(MESSAGE_PATTERN, termId, eventId));
        }
    }

    class ParticipantLimitTooLow extends DomainException {

        private static final String MESSAGE_PATTERN = "Participant limit (%d) for term %s too low.";

        public ParticipantLimitTooLow(TermId termId, int participantLimit) {
            super(String.format(MESSAGE_PATTERN, participantLimit, termId));
        }
    }

    class ParticipantLimitTooHigh extends DomainException {

        private static final String MESSAGE_PATTERN = "Participant limit (%d) for term %s too high.";

        public ParticipantLimitTooHigh(TermId termId, int participantLimit) {
            super(String.format(MESSAGE_PATTERN, participantLimit, termId));
        }
    }
}
