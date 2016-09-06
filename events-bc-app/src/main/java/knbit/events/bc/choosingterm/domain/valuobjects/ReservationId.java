package knbit.events.bc.choosingterm.domain.valuobjects;

import knbit.events.bc.common.domain.UUIDBasedIdentifier;

/**
 * Created by novy on 19.08.15.
 */
public class ReservationId extends UUIDBasedIdentifier {

    public ReservationId() {
        super();
    }

    protected ReservationId(String id) {
        super(id);
    }

    public static ReservationId of(String id) {
        return new ReservationId(id);
    }
}
