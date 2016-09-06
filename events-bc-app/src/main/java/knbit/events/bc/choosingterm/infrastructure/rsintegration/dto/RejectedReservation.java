package knbit.events.bc.choosingterm.infrastructure.rsintegration.dto;

import lombok.Data;

@Data
public class RejectedReservation {
    private String eventId;
    private String reservationId;
}
