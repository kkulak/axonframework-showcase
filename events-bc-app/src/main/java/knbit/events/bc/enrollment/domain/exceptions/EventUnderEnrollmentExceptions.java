package knbit.events.bc.enrollment.domain.exceptions;

import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;

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

    class AlreadyTransitedToReady extends DomainException {

        private static final String MESSAGE_PATTERN = "Event %s already transited to ready.";

        public AlreadyTransitedToReady(EventId eventId) {
            super(String.format(MESSAGE_PATTERN, eventId));
        }
    }

    class AlreadyCancelled extends DomainException {

        private static final String MESSAGE_PATTERN = "Event %s has been cancelled.";

        public AlreadyCancelled(EventId eventId) {
            super(String.format(MESSAGE_PATTERN, eventId));
        }
    }
}
