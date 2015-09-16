package knbit.rsintegration.bc.scheduling.response

import akka.actor.ActorLogging
import akka.persistence.PersistentActor
import knbit.rsintegration.bc.common.{Reservation, Term}
import knbit.rsintegration.bc.scheduling.response.State.State
import scala.concurrent.duration._
import scala.language.postfixOps

object State extends Enumeration {
  type State = Value
  val INITIALIZED = Value("initialized")
  val NOT_INITIALIZED = Value("!initialized")
  val FINISHED = Value("finished")
  val TERMINATED = Value("terminated")
  val REJECTED = Value("rejected")
}

class ResponseActor(id: String) extends PersistentActor with ActorLogging {
  import context._

  var requestId: String = _
  var reservation: Reservation = _
  var responseStrategy: ResponseStrategy = _
  var schedulingStrategy: ResponseSchedulingStrategy = _
  var state: State = State.NOT_INITIALIZED

  override def persistenceId: String = id

  override def receiveRecover: Receive = {
    case evt: ResponseInitializedEvent => onResponseInitializedEvt(evt)
    case ResponseFinishedEvent => onResponseFinishedEvt()
    case UnresolvedResponseEvent => onUnresolvedEvt()
    case FailureReservationEvent => onFailureEvt()
    case ResponseTerminatedEvent => onResponseTerminatedEvt()
    case RejectedResponseEvent => onResponseRejectedEvt()
  }

  override def receiveCommand: Receive = {
    case InitializeResponseCommand(reqId, res, respStrategy, schStrategy) =>
      initialize(reqId, res, respStrategy, schStrategy)
    case CheckResponseCommand => checkResponse()
  }

  private[this] def onResponseInitializedEvt(evt: ResponseInitializedEvent): Unit = {
    this.requestId = evt.requestId
    this.reservation = evt.reservation
    this.responseStrategy = evt.responseStrategy
    this.schedulingStrategy = evt.schedulingStrategy
    this.state = State.INITIALIZED
  }

  private[this] def onResponseFinishedEvt(): Unit = {
    state = State.FINISHED
    stop(self)
  }

  private[this] def onResponseTerminatedEvt(): Unit = {
    state = State.TERMINATED
    stop(self)
  }

  private[this] def onResponseRejectedEvt(): Unit = {
    state = State.REJECTED
    stop(self)
  }

  private[this] def onUnresolvedEvt(): Unit = {
    schedulingStrategy.markAttempt()
  }

  private[this] def onFailureEvt(): Unit = {
    schedulingStrategy.markFailureAttempt()
  }

  private[this] def initialize(reqId: String, res: Reservation, respStrategy: ResponseStrategy,
                               schStrategy: ResponseSchedulingStrategy): Unit = {
    if(res.reservationId != id) throw new IllegalArgumentException("reservation.id != id")
    if(state != State.NOT_INITIALIZED) throw new IllegalStateException("state != NOT_INITIALIZED")

    persist(
      ResponseInitializedEvent(reqId, res, respStrategy, schStrategy)
    ){ evt =>
      onResponseInitializedEvt(evt)
    }
  }

  private[this] def checkResponse(): Unit = {
    if(state != State.INITIALIZED) throw new IllegalStateException("state != INITIALIZED")

    schedulingStrategy.shouldContinue() match {
      case true => makeRequest()
      case false => terminate()
    }
  }

  private[this] def makeRequest(): Unit = {
    responseStrategy.checkResponse(requestId) match {
      case Success(term) => onSuccess(term)
      case Unresolved() => onUnresolved()
      case Rejection() => onRejection()
      case Failure() => onFailure()
    }
  }

  private[this] def terminate(): Unit = {
    log.info("Checking response exceeded max attempt amount. Terminating...")
    persist(ResponseTerminatedEvent) { evt =>
      system.eventStream.publish(ResponseExceedMaxAttemptAmountEvent(requestId, reservation))
      onResponseTerminatedEvt()
    }
  }

  private[this] def onSuccess(term: Term): Unit = {
    log.info("Success response. Term: [{}]", term)
    persist(ResponseFinishedEvent){ evt =>
      system.eventStream.publish(SuccessReservationEvent(reservation.eventId, reservation.reservationId, term))
      onResponseFinishedEvt()
    }
  }

  private[this] def onUnresolved(): Unit = {
    log.info("Unresolved response")
    persist(UnresolvedResponseEvent){ evt =>
      onUnresolvedEvt()
      schedulingStrategy.schedule
    }
  }

  private[this] def onRejection(): Unit = {
    log.info("Rejection response")
    persist(RejectedResponseEvent){ evt =>
      system.eventStream.publish(FailureReservationEvent(reservation.eventId, reservation.reservationId))
      onResponseRejectedEvt()
    }
  }

  private[this] def onFailure(): Unit = {
    log.info("Failure response")
    persist(FailureReservationEvent){ evt =>
      onFailureEvt()
      schedulingStrategy.schedule
    }
  }

}
