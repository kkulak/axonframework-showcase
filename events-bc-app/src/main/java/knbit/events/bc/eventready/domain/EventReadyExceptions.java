package knbit.events.bc.eventready.domain;

import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId;

/**
 * Created by novy on 22.11.15.
 */
public interface EventReadyExceptions {

    class AlreadyCancelled extends RuntimeException {
        private static final String ERROR_MESSAGE_TEMPLATE = "ReadyEvent %s has been cancelled before!";

        public AlreadyCancelled(ReadyEventId eventId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, eventId));
        }
    }
}
