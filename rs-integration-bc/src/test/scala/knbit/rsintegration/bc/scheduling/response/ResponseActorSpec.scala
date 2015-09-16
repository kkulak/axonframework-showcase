package knbit.rsintegration.bc.scheduling.response

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import knbit.rsintegration.bc.EventsForwarder
import knbit.rsintegration.bc.common.{Reservation, Term}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
 * Corner cases, i.e. throwing exception from actor,
 * should be tested along with parent actor & supervisor strategy.
 * In general, whole test below is hacked(mock PersistentView) :(
 * TODO: Builder pattern?
 */

class ResponseActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
            with WordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar {

  def this() = this(ActorSystem("ResponseActorSpec"))

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "ResponseActor" must {

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
      val requestId = "request-id"
      val respStrategy = mock[ResponseStrategy]
      val schedulingStrategy = mock[ResponseSchedulingStrategy]
      val reservation = Reservation("eid", "0", 1000L, 90L, 10)
      // when
      req ! InitializeResponseCommand(requestId, reservation, respStrategy, schedulingStrategy)
      // then
      expectNoMsg()
    }

    "initialize itself given valid initialize command" in {
      // given
      val req = newInstance("2")
      val requestId = "request-id"
      val respStrategy = mock[ResponseStrategy]
      val schedulingStrategy = mock[ResponseSchedulingStrategy]
      val reservation = Reservation("eid", "2", 1000L, 90L, 10)
      // when
      req ! InitializeResponseCommand(requestId, reservation, respStrategy, schedulingStrategy)
      // then
      expectMsg(ResponseInitializedEvent(requestId, reservation, respStrategy, schedulingStrategy))
      expectNoMsg()
    }

    "terminate execution given check response command when actor is not initialized" in {
      // given
      val req = newInstance("3")
      // when
      req ! CheckResponseCommand
      // then
      expectNoMsg()
    }

    "terminate actor given check response command when max attempt amount has been exceeded" in {
      // given
      val req = newInstance("4")
      val requestId = "request-id"
      val respStrategy = mock[ResponseStrategy]
      val schedulingStrategy = mock[ResponseSchedulingStrategy]
      when(schedulingStrategy.shouldContinue()).thenReturn(false)
      val reservation = Reservation("eid", "4", 1000L, 90L, 10)
      // when
      req ! InitializeResponseCommand(requestId, reservation, respStrategy, schedulingStrategy)
      req ! CheckResponseCommand
      // then
      expectMsg(ResponseInitializedEvent(requestId, reservation, respStrategy, schedulingStrategy))
      expectMsg(ResponseTerminatedEvent)
      expectNoMsg()
    }

    "note failed response given check response command with failing response strategy" in {
      // given
      val req = newInstance("5")
      val requestId = "requestid"
      val respStrategy = mock[ResponseStrategy]
      val schedulingStrategy = mock[ResponseSchedulingStrategy]
      val reservation = Reservation("eid", "5", 1000L, 90L, 10)
      when(schedulingStrategy.shouldContinue()).thenReturn(true)
      when(respStrategy.checkResponse(requestId)).thenReturn(Failure())
      // when
      req ! InitializeResponseCommand(requestId, reservation, respStrategy, schedulingStrategy)
      req ! CheckResponseCommand
      // then
      expectMsg(ResponseInitializedEvent(requestId, reservation, respStrategy, schedulingStrategy))
      expectMsg(FailureReservationEvent)
      expectNoMsg()
    }

    "note successful response given check response command with succeeding response strategy" in {
      // given
      val req = newInstance("6")
      val requestId = "requestid"
      val respStrategy = mock[ResponseStrategy]
      val schedulingStrategy = mock[ResponseSchedulingStrategy]
      val reservation = Reservation("eid", "6", 1000L, 90L, 10)
      when(schedulingStrategy.shouldContinue()).thenReturn(true)
      when(respStrategy.checkResponse(requestId)).thenReturn(Success(Term()))
      // when
      req ! InitializeResponseCommand(requestId, reservation, respStrategy, schedulingStrategy)
      req ! CheckResponseCommand
      // then
      expectMsg(ResponseInitializedEvent(requestId, reservation, respStrategy, schedulingStrategy))
      expectMsg(ResponseFinishedEvent)
      expectNoMsg()
    }

    "note rejected response given check response command with rejecting response strategy" in {
      // given
      val req = newInstance("7")
      val requestId = "requestid"
      val respStrategy = mock[ResponseStrategy]
      val schedulingStrategy = mock[ResponseSchedulingStrategy]
      val reservation = Reservation("eid", "7", 1000L, 90L, 10)
      when(schedulingStrategy.shouldContinue()).thenReturn(true)
      when(respStrategy.checkResponse(requestId)).thenReturn(Rejection())
      // when
      req ! InitializeResponseCommand(requestId, reservation, respStrategy, schedulingStrategy)
      req ! CheckResponseCommand
      // then
      expectMsg(ResponseInitializedEvent(requestId, reservation, respStrategy, schedulingStrategy))
      expectMsg(RejectedResponseEvent)
      expectNoMsg()
    }

    "note unresolved response given check response command with unresolving response strategy" in {
      // given
      val req = newInstance("8")
      val requestId = "requestid"
      val respStrategy = mock[ResponseStrategy]
      val schedulingStrategy = mock[ResponseSchedulingStrategy]
      val reservation = Reservation("eid", "8", 1000L, 90L, 10)
      when(schedulingStrategy.shouldContinue()).thenReturn(true)
      when(respStrategy.checkResponse(requestId)).thenReturn(Unresolved())
      // when
      req ! InitializeResponseCommand(requestId, reservation, respStrategy, schedulingStrategy)
      req ! CheckResponseCommand
      // then
      expectMsg(ResponseInitializedEvent(requestId, reservation, respStrategy, schedulingStrategy))
      expectMsg(UnresolvedResponseEvent)
      expectNoMsg()
    }

  }

  private[this] def newInstance(id: String): ActorRef = {
    system.actorOf(Props(classOf[EventsForwarder], self, id))
    system.actorOf(Props(classOf[ResponseActor], id))
  }

}
