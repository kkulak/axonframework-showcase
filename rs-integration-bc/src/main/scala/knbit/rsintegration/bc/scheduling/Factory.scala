package knbit.rsintegration.bc.scheduling

import akka.actor.{ActorRef, ActorSystem, Props}
import knbit.rsintegration.bc.common.Reservation
import knbit.rsintegration.bc.scheduling.request.{FailureAttemptAmountSchedulingStrategy, InitializeRequestCommand, MockRequestStrategy, RequestActor}
import knbit.rsintegration.bc.scheduling.response.{AttemptAmountSchedulingStrategy, InitializeResponseCommand, MockResponseStrategy, ResponseActor}

object Factory {

  def createRequest(reservation: Reservation)(implicit system: ActorSystem): ActorRef = {
    val requestActor = system.actorOf(
       Props(classOf[RequestActor],
         reservation.reservationId)
    )
    requestActor ! InitializeRequestCommand(
      reservation, MockRequestStrategy(), FailureAttemptAmountSchedulingStrategy(10)
    )
    requestActor
  }

  def createResponse(requestId: String, reservation: Reservation)(implicit system: ActorSystem): ActorRef = {
    val responseActor = system.actorOf(
      Props(classOf[ResponseActor],
      reservation.reservationId)
    )
    responseActor ! InitializeResponseCommand(
      requestId, reservation, MockResponseStrategy(), AttemptAmountSchedulingStrategy(10, 100)
    )
    responseActor
  }

}
