package knbit.events.bc.choosingterm.infrastructure.rsintegration;

import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents;
import knbit.events.bc.common.config.AMQPConstants;
import knbit.events.bc.common.dispatcher.MessageDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoomBookingEventHandler {
    private final MessageDispatcher dispatcher;

    @Autowired
    public RoomBookingEventHandler(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @EventHandler
    public void handle(ReservationEvents.RoomRequested event) {
        final ReservationDTO reservationDTO = new ReservationDTO(
                event.eventId().value(),
                event.reservationId().value(),
                event.eventDuration().start().getMillis(),
                event.eventDuration().duration().getStandardMinutes(),
                event.capacity().value()
        );

        log.debug("Sending reservation request to rs-integration-bc...");
        dispatcher.dispatch(reservationDTO, AMQPConstants.RS_INTEGRATION_QUEUE);
    }

}
