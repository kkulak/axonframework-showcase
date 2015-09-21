package knbit.events.bc.eventproposal.domain.sagas;

import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReservationNotifications {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Accepted {
        String eventId;
        String reservationId;
        String name;
    }

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Rejected {
        String eventId;
        String reservationId;
        String name;
    }

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Failed {
        String eventId;
        String reservationId;
        String name;
        String cause;
    }

}
