package knbit.rsintegration.bc.scheduling

import akka.actor.Actor
import knbit.rsintegration.bc.scheduling.request.RequestFinishedEvent

class EventHandler(factory: ActorFactory) extends Actor {
  import context._

  override def receive: Receive = {
    case RequestFinishedEvent(requestId, reservation) => factory.createResponse(requestId, reservation)
  }

}
