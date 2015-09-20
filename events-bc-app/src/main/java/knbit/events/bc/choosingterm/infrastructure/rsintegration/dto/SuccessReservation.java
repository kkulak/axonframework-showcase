package knbit.events.bc.choosingterm.infrastructure.rsintegration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessReservation {
    private String eventId;
    private String reservationId;
    private Term term;
}
