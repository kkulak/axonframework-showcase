package knbit.rsintegration.bc.scheduling

import akka.actor.Actor
import knbit.rsintegration.bc.scheduling.request.RequestFinishedEvent

class EventHandlerActor extends Actor {
  import context._

  override def receive: Receive = {
    case RequestFinishedEvent(requestId, reservation) => Factory.createResponse(requestId, reservation)
  }

}
