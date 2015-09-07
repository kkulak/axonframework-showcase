package knbit.rsintegration.bc.scheduling

import akka.actor.Actor

class ResponseActor extends Actor {

  override def receive: Receive = {
    case ResponseRoomReservationCommand(responseId, eventId, reservationId, start, duration, capacity) => println("received response reservation command")
  }

}
