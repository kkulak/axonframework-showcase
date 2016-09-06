package knbit.events.bc.choosingterm.infrastructure.rsintegration.dto;

import lombok.Data;

@Data
public class FailedReservation {
    private String eventId;
    private String reservationId;
    private String cause;
}
