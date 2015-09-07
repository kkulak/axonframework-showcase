package knbit.rsintegration.bc.scheduling

import akka.actor.Actor

import scala.util.Random

class RequestActor extends Actor {

  override def receive: Receive = {
    case RequestRoomReservationCommand(requestId, eventId, reservationId, start, duration, capacity) => {
      println("received request command")
      // dummy impl, just to test scheduling
      if(new Random().nextInt(5) == 3) {
        sender() ! CancelRoomReservationRequestCommand(requestId)
        sender() ! ScheduleRoomReservationResponseCommand(eventId, reservationId, start, duration, capacity)
      }
    }
  }

}
