package knbit.events.bc.choosingterm.domain.exceptions;

import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;

/**
 * Created by novy on 19.08.15.
 */
public interface ReservationExceptions {

    class ReservationDoesNotExist extends RuntimeException {
        private static final String ERROR_MESSAGE_TEMPLATE = "Reservation with ID %s doesn't exist!";

        public ReservationDoesNotExist(ReservationId reservationId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, reservationId));
        }
    }

    class ReservationAcceptedException extends RuntimeException {
        private static final String ERROR_MESSAGE_TEMPLATE = "Reservation with ID %s already cancelled!";

        public ReservationAcceptedException(ReservationId reservationId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, reservationId));
        }
    }

    class ReservationRejectedException extends RuntimeException {
        private static final String ERROR_MESSAGE_TEMPLATE = "Reservation with ID %s already rejected!";

        public ReservationRejectedException(ReservationId reservationId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, reservationId));
        }
    }

    class ReservationCancelledException extends RuntimeException {
        private static final String ERROR_MESSAGE_TEMPLATE = "Reservation with ID %s cancelled!";

        public ReservationCancelledException(ReservationId reservationId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, reservationId));
        }
    }

    class ReservationFailedException extends RuntimeException {
        private static final String ERROR_MESSAGE_TEMPLATE = "Reservation with ID %s failed!";

        public ReservationFailedException(ReservationId reservationId) {
            super(String.format(ERROR_MESSAGE_TEMPLATE, reservationId));
        }
    }

}
