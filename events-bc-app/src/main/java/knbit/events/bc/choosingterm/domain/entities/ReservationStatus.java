package knbit.events.bc.choosingterm.domain.entities;

import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions;
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;

/**
 * Created by novy on 19.08.15.
 */
enum ReservationStatus {

    PENDING {
        @Override
        public void rejectOn(ReservationId reservationId) {
        }
    },
    ACCEPTED {
        @Override
        public void rejectOn(ReservationId reservationId) {
            throw new ReservationExceptions.ReservationAcceptedException(reservationId);
        }
    },
    REJECTED {
        @Override
        public void rejectOn(ReservationId reservationId) {
            throw new ReservationExceptions.ReservationRejectedException(reservationId);
        }
    },
    CANCELLED {
        @Override
        public void rejectOn(ReservationId reservationId) {
            throw new ReservationExceptions.ReservationCancelledException(reservationId);
        }
    },
    FAILED {
        @Override
        public void rejectOn(ReservationId reservationId) {
            throw new ReservationExceptions.ReservationFailedException(reservationId);
        }
    };

    public abstract void rejectOn(ReservationId reservationId);

}
