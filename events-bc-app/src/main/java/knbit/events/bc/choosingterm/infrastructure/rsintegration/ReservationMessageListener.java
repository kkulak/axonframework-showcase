package knbit.events.bc.choosingterm.infrastructure.rsintegration;

import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.ReservationCommands;
import knbit.events.bc.choosingterm.infrastructure.rsintegration.dto.FailedReservation;
import knbit.events.bc.choosingterm.infrastructure.rsintegration.dto.RejectedReservation;
import knbit.events.bc.choosingterm.infrastructure.rsintegration.dto.SuccessReservation;
import knbit.events.bc.common.config.AMQPConstants;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static knbit.events.bc.common.infrastructure.ObjectUtils.map;

/**
 * TODO: refactor. Use @RabbitHandler & @Payload
 */

@Slf4j
@Component
public class ReservationMessageListener {
    private final CommandGateway gateway;

    private final static String HEADER_KEY = "type";
    private final static String SUCCESS = "rs-integration:success";
    private final static String FAILED = "rs-integration:failure";
    private final static String REJECTED = "rs-integration:rejected";
    private final static String UNKNOWN = "unknown";

    @Autowired
    public ReservationMessageListener(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @RabbitListener(queues = AMQPConstants.RS_INTEGRATION_OUT_QUEUE)
    public void on(Message message) {
        log.debug("Received message from rs-integration-bc: {}", new String(message.getBody()));

        final String messageType = resolve(message);
        switch (messageType) {
            case SUCCESS: {
                onSuccess(message);
                break;
            }
            case FAILED: {
                onFailure(message);
                break;
            }
            case REJECTED: {
                onRejection(message);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown or malformed message type");
            }
        }
    }

    private void onFailure(Message message) {
        final FailedReservation reservation = map(message.getBody(), FailedReservation.class);
        gateway.send(ReservationCommands.FailReservation.of(
                EventId.of(reservation.getEventId()),
                ReservationId.of(reservation.getReservationId()),
                reservation.getCause()
        ));
    }

    private void onRejection(Message message) {
        final RejectedReservation reservation = map(message.getBody(), RejectedReservation.class);
        gateway.send(ReservationCommands.RejectReservation.of(
                EventId.of(reservation.getEventId()),
                ReservationId.of(reservation.getReservationId())
        ));
    }

    private void onSuccess(Message message) {
        final SuccessReservation reservation = map(message.getBody(), SuccessReservation.class);
        gateway.send(ReservationCommands.AcceptReservation.of(
                EventId.of(reservation.getEventId()),
                ReservationId.of(reservation.getReservationId()),
                reservation.getTerm().getLocation()
        ));
    }

    private String resolve(Message message) {
        final MessageProperties properties = message.getMessageProperties();
        return (String) properties.getHeaders().getOrDefault(HEADER_KEY, UNKNOWN);
    }

}
