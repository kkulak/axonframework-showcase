package knbit.rsintegration.bc.scheduling

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import knbit.rsintegration.bc.EventsForwarder
import knbit.rsintegration.bc.common.Reservation
import knbit.rsintegration.bc.scheduling.request._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.mockito.Mockito._

/**
 * Corner cases, i.e. throwing exception from actor,
 * should be tested along with parent actor & supervisor strategy.
 * In general, whole test below is hacked(mock PersistentView) :(
 */

class RequestActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
            with WordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar {

  def this() = this(ActorSystem("RequestActorSpec"))

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "RequestActor" must {

    "ignore unknown commands" in {
      // given
      val req = newInstance("0")
      // when
      req ! "Unexpected command"
      // then
      expectNoMsg()
    }

    "terminate execution given initialize command with invalid reservation id" in {
      // given
      val req = newInstance("1")
      val reqStrategy = mock[RequestStrategy]
      val schedulingStrategy = mock[RequestSchedulingStrategy]
      val reservation = Reservation("eid", "0", 1000L, 90L, 10)
      // when
      req ! InitializeRequestCommand(reservation, reqStrategy, schedulingStrategy)
      // then
      expectNoMsg()
    }

    "initialize itself given valid initialize command" in {
      // given
      val req = newInstance("2")
      val reqStrategy = mock[RequestStrategy]
      val schedulingStrategy = mock[RequestSchedulingStrategy]
      val reservation = Reservation("eid", "2", 1000L, 90L, 10)
      // when
      req ! InitializeRequestCommand(reservation, reqStrategy, schedulingStrategy)
      // then
      expectMsg(RequestInitializedEvent(reservation, reqStrategy, schedulingStrategy))
      expectNoMsg()
    }

    "terminate execution given send request command when actor is not initialized" in {
      // given
      val req = newInstance("3")
      // when
      req ! SendRequestCommand
      // then
      expectNoMsg()
    }

    "terminate actor given send request command when max attempt amount has been exceeded" in {
      // given
      val req = newInstance("4")
      val reqStrategy = mock[RequestStrategy]
      val schedulingStrategy = mock[RequestSchedulingStrategy]
      when(schedulingStrategy.shouldContinue()).thenReturn(false)
      val reservation = Reservation("eid", "4", 1000L, 90L, 10)
      // when
      req ! InitializeRequestCommand(reservation, reqStrategy, schedulingStrategy)
      req ! SendRequestCommand
      // then
      expectMsg(RequestInitializedEvent(reservation, reqStrategy, schedulingStrategy))
      expectMsg(RequestTerminatedEvent)
      expectNoMsg()
    }

    "note failed request given send request command with failing request strategy" in {
      // given
      val req = newInstance("5")
      val reqStrategy = mock[RequestStrategy]
      val schedulingStrategy = mock[RequestSchedulingStrategy]
      val reservation = Reservation("eid", "5", 1000L, 90L, 10)
      when(schedulingStrategy.shouldContinue()).thenReturn(true)
      when(reqStrategy.makeRequest(reservation)).thenReturn(Failure())
      // when
      req ! InitializeRequestCommand(reservation, reqStrategy, schedulingStrategy)
      req ! SendRequestCommand
      // then
      expectMsg(RequestInitializedEvent(reservation, reqStrategy, schedulingStrategy))
      expectMsg(RequestFailedEvent)
      expectNoMsg()
    }

    "note successful request given send request command with succeeding request strategy" in {
      // given
      val req = newInstance("6")
      val reqStrategy = mock[RequestStrategy]
      val schedulingStrategy = mock[RequestSchedulingStrategy]
      val reservation = Reservation("eid", "6", 1000L, 90L, 10)
      when(schedulingStrategy.shouldContinue()).thenReturn(true)
      when(reqStrategy.makeRequest(reservation)).thenReturn(Success("req-id"))
      // when
      req ! InitializeRequestCommand(reservation, reqStrategy, schedulingStrategy)
      req ! SendRequestCommand
      // then
      expectMsg(RequestInitializedEvent(reservation, reqStrategy, schedulingStrategy))
      expectMsg(RequestFinishedEvent)
      expectNoMsg()
    }

  }

  private[this] def newInstance(id: String): ActorRef = {
    system.actorOf(Props(classOf[EventsForwarder], self, id))
    system.actorOf(Props(classOf[RequestActor], id))
  }

}
