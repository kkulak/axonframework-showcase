package knbit.rsintegration.bc.scheduling

import akka.actor.{ActorLogging, Actor}
import knbit.rsintegration.bc.amqp.AMQP
import knbit.rsintegration.bc.common._
import knbit.rsintegration.bc.scheduling.request.{RequestExceedMaxAttemptAmountEvent, RequestSucceededEvent}
import knbit.rsintegration.bc.scheduling.response.{ResponseExceedMaxAttemptAmountEvent, RejectedReservationEvent, FailureReservationEvent, SuccessReservationEvent}
import com.thenewmotion.akka.rabbitmq._

class EventHandler(factory: ActorFactory) extends Actor with ActorLogging {
  val publisher = context.actorSelection("/user/rabbitmq/publisher")

  override def receive: Receive = {
    case RequestSucceededEvent(requestId, reservation) => factory.createResponse(requestId, reservation)
    case RequestExceedMaxAttemptAmountEvent(eventId, reservationId) => onRequestTimeout(eventId, reservationId)
    case SuccessReservationEvent(eventId, reservationId, term) => onSuccessReservation(eventId, reservationId, term)
    case RejectedReservationEvent(eventId, reservationId) => onRejectedReservation(eventId, reservationId)
    case ResponseExceedMaxAttemptAmountEvent(eventId, reservationId) => onRequestTimeout(eventId, reservationId)
  }

  private[this] def onRequestTimeout(eventId: String, reservationId: String) = {
    val message = ReservationTimeout(eventId, reservationId)
    log.info("Sending success reservation message: [{}]", message)
    publisher ! ChannelMessage(AMQP.publish(message), dropIfNoChannel = false)
  }

  private[this] def onSuccessReservation(eventId: String, reservationId: String, term: Term): Unit = {
    val message = AcceptedReservation(eventId, reservationId, term)
    log.info("Sending success reservation message: [{}]", message)
    publisher ! ChannelMessage(AMQP.publish(message), dropIfNoChannel = false)
  }

  private[this] def onRejectedReservation(eventId: String, reservationId: String): Unit = {
    val message = RejectedReservation(eventId, reservationId)
    log.info("Sending failure reservation message: [{}]", message)
    publisher ! ChannelMessage(AMQP.publish(message), dropIfNoChannel = false)
  }

}
