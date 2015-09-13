package knbit.rsintegration.bc.scheduling

import akka.actor.{ActorLogging, Actor}
import knbit.rsintegration.bc.amqp.AMQP
import knbit.rsintegration.bc.common.{RejectedReservation, AcceptedReservation}
import knbit.rsintegration.bc.scheduling.request.RequestSucceededEvent
import knbit.rsintegration.bc.scheduling.response.{FailureReservationEvent, SuccessReservationEvent}
import com.thenewmotion.akka.rabbitmq._

class EventHandler(factory: ActorFactory) extends Actor with ActorLogging {

  override def receive: Receive = {
    case RequestSucceededEvent(requestId, reservation) => factory.createResponse(requestId, reservation)
    case evt: SuccessReservationEvent => send(evt)
    case evt: FailureReservationEvent => send(evt)
  }

  private[this] def send(evt: SuccessReservationEvent): Unit = {
    val message = AcceptedReservation(evt.eventId, evt.reservationId, evt.term)
    val publisher = context.actorSelection("/user/rabbitmq/publisher")
    log.info("Sending success reservation message: [{}]", message)
    publisher ! ChannelMessage(AMQP.publish(message), dropIfNoChannel = false)
  }

  private[this] def send(evt: FailureReservationEvent): Unit = {
    val message = RejectedReservation(evt.eventId, evt.reservationId)
    val publisher = context.actorSelection("/user/rabbitmq/publisher")
    log.info("Sending failure reservation message: [{}]", message)
    publisher ! ChannelMessage(AMQP.publish(message), dropIfNoChannel = false)
  }

}
