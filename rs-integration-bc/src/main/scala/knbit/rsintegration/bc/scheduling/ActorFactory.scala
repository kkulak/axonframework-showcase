package knbit.rsintegration.bc.scheduling

import akka.actor.{ActorRef, ActorSystem, Props}
import knbit.rsintegration.bc.common.Reservation
import knbit.rsintegration.bc.scheduling.request.RequestSchedulingStrategy
import knbit.rsintegration.bc.scheduling.request._
import knbit.rsintegration.bc.scheduling.response._

class ActorFactory( requestStrategy: RequestStrategy,
                    requestSchedulingStrategy: RequestSchedulingStrategy,
                    responseStrategy: ResponseStrategy,
                    responseSchedulingStrategy: ResponseSchedulingStrategy,
                    system: ActorSystem) {

  def createRequest(reservation: Reservation): ActorRef = {
    val requestActor = system.actorOf(
       Props(classOf[RequestActor],
         reservation.reservationId)
    )
    requestActor ! InitializeRequestCommand(
      reservation, requestStrategy, requestSchedulingStrategy
    )
    requestActor
  }

  def createResponse(requestId: String, reservation: Reservation): ActorRef = {
    val responseActor = system.actorOf(
      Props(classOf[ResponseActor],
      reservation.reservationId)
    )
    responseActor ! InitializeResponseCommand(
      requestId, reservation, responseStrategy, responseSchedulingStrategy
    )
    responseActor
  }

}
