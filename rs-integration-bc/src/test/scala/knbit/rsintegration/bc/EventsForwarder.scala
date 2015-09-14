package knbit.rsintegration.bc

import akka.actor.ActorRef
import akka.persistence.PersistentView

//noinspection ScalaDeprecation
class EventsForwarder(receiver: ActorRef, id: String) extends PersistentView {

  override def persistenceId: String = id
  override def viewId: String = s"$id-view"

  def receive: Receive = {
    case payload => receiver ! payload
  }

}
