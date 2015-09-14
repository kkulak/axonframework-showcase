package knbit.rsintegration.bc.scheduling

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import knbit.rsintegration.bc.EventsForwarder
import knbit.rsintegration.bc.scheduling.request.RequestActor
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class RequestActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
            with WordSpecLike with Matchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("RequestActorSpec"))

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "RequestActor" must {

    "ignore unknown commands" in {
      val req = newInstance("0")
      req ! "Unexpected command"
      expectNoMsg()
    }

  }

  private[this] def newInstance(id: String): ActorRef = {
    system.actorOf(Props(classOf[EventsForwarder], self, s"request-$id"))
    system.actorOf(Props(classOf[RequestActor], "0"))
  }

}
