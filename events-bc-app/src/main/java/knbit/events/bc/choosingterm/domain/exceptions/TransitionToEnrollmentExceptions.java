package knbit.events.bc.choosingterm.domain.exceptions;

import knbit.events.bc.common.domain.valueobjects.EventId;

/**
 * Created by novy on 02.10.15.
 */
public interface TransitionToEnrollmentExceptions {

    class HasPendingReservations extends RuntimeException {
        private static final String ERROR_MESSAGE_TEMPLATE = "Event with id %s has pending reservations!";

        public HasPendingReservations(EventId eventId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, eventId));
        }
    }

    class DoesNotHaveAnyTerms extends RuntimeException {
        private static final String ERROR_MESSAGE_TEMPLATE = "Event with id %s does't have any terms assigned!";

        public DoesNotHaveAnyTerms(EventId eventId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, eventId));
        }
    }
}
