package knbit.events.bc.eventready.domain;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId;

/**
 * Created by novy on 22.11.15.
 */
public interface EventReadyExceptions {

    class AlreadyCancelled extends DomainException {
        private static final String ERROR_MESSAGE_TEMPLATE = "ReadyEvent %s has been cancelled before!";

        public AlreadyCancelled(ReadyEventId eventId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, eventId));
        }
    }

    class AlreadyMarkedItTookPlace extends DomainException {
        private static final String ERROR_MESSAGE_TEMPLATE = "ReadyEvent %s has been marked it took place before!";

        public AlreadyMarkedItTookPlace(ReadyEventId eventId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, eventId));
        }
    }
}
