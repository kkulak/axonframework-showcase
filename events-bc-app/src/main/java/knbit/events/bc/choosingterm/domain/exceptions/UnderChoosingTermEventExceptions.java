package knbit.events.bc.choosingterm.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;

/**
 * Created by novy on 02.10.15.
 */
public interface UnderChoosingTermEventExceptions {

    class AlreadyTransitedToEnrollment extends DomainException {
        private static final String ERROR_MESSAGE_TEMPLATE = "Enrollment has already started for event with id %s!";

        public AlreadyTransitedToEnrollment(EventId eventId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, eventId));
        }
    }

    class AlreadyCancelled extends DomainException {
        private static final String ERROR_MESSAGE_TEMPLATE = "UnderChoosingTermEvent %s has been cancelled before!";

        public AlreadyCancelled(EventId eventId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, eventId));
        }
    }
}
