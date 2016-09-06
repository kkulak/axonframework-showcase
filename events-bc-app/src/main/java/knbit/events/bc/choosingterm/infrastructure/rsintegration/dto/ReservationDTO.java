package knbit.events.bc.choosingterm.infrastructure.rsintegration.dto;

import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationDTO {

    private String eventId;
    private String reservationId;
    private long start;
    private long duration;
    private int capacity;

}
