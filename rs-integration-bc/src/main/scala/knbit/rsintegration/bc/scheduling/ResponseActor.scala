package knbit.rsintegration.bc.scheduling

import akka.actor.Actor

class ResponseActor extends Actor {

  override def receive: Receive = {
    case ResponseRoomReservationCommand(responseId, reservation) => println("received response reservation command")
  }

}
