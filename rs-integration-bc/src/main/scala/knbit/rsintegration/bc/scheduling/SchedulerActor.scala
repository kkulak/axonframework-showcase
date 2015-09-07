package knbit.rsintegration.bc.scheduling

import java.util.UUID

import akka.actor.{Cancellable, Props, Actor}
import scala.concurrent.duration._
import scala.collection.mutable

class SchedulerActor extends Actor {
  import context._

  val requestReservations: mutable.Map[String, Cancellable] = mutable.Map()
  val responseReservations: mutable.Map[String, Cancellable] = mutable.Map()

  override def receive: Receive = {
    case ScheduleRoomReservationRequestCommand(eventId, reservationId, start, duration, capacity) => {
      val requestActor = actorOf(Props[RequestActor])
      val requestId = UUID.randomUUID().toString
      val command = RequestRoomReservationCommand(requestId, eventId, reservationId, start, duration, capacity)
      val cancellable = system.scheduler.schedule(0 seconds, 5 seconds, requestActor, command)
      requestReservations(requestId) = cancellable
      println("scheduled request actor")
    }
    case CancelRoomReservationRequestCommand(requestId) => {
      requestReservations(requestId).cancel()
      requestReservations.remove(requestId)
      println("cancelled request actor scheduling")
    }
    case ScheduleRoomReservationResponseCommand(eventId, reservationId, start, duration, capacity) => {
      val responseActor = actorOf(Props[ResponseActor])
      val responseId = UUID.randomUUID().toString
      val command = ResponseRoomReservationCommand(responseId, eventId, reservationId, start, duration, capacity)
      val cancellable = system.scheduler.schedule(0 seconds, 10 seconds, responseActor, command)
      responseReservations(responseId) = cancellable
      println("scheduled response actor")
    }
  }

}