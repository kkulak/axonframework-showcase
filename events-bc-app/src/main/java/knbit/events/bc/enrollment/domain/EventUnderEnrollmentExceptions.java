package knbit.events.bc.enrollment.domain;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;

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
}
