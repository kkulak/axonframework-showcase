package knbit.rsintegration.bc.scheduling.request

import akka.actor.ActorLogging
import akka.persistence.{RecoveryCompleted, PersistentActor}
import knbit.rsintegration.bc.common.Reservation
import knbit.rsintegration.bc.scheduling.request.State.State

import scala.language.postfixOps

object State extends Enumeration {
  type State = Value
  val INITIALIZED = Value("initialized")
  val NOT_INITIALIZED = Value("!initialized")
  val FINISHED = Value("finished")
  val TERMINATED = Value("terminated")
}

class RequestActor(id: String) extends PersistentActor with ActorLogging {
  import context._

  var requestStrategy: RequestStrategy = _
  var schedulingStrategy: RequestSchedulingStrategy = _
  var reservation: Reservation = _
  var state: State = State.NOT_INITIALIZED

  override def persistenceId: String = id

  override def receiveCommand: Receive = {
    case InitializeRequestCommand(res, reqStrategy, schStrategy) => initialize(res, reqStrategy, schStrategy)
    case SendRequestCommand => sendRequest()
  }

  override def receiveRecover: Receive = {
    case evt: RequestInitializedEvent => onRequestInitializedEvt(evt)
    case RequestFinishedEvent => onRequestFinishedEvt()
    case RequestFailedEvent => onRequestFailedEvt()
    case RequestTerminatedEvent => onRequestTerminatedEvt()
  }

  private[this] def onRequestInitializedEvt(evt: RequestInitializedEvent): Unit = {
    this.requestStrategy = evt.requestStrategy
    this.schedulingStrategy = evt.schedulingStrategy
    this.reservation = evt.reservation
    this.state = State.INITIALIZED
  }

  private[this] def onRequestTerminatedEvt(): Unit = {
    this.state = State.TERMINATED
    stop(self)
  }

  private[this] def onRequestFinishedEvt(): Unit = {
    this.state = State.FINISHED
    stop(self)
  }

  private[this] def onRequestFailedEvt(): Unit = {
    schedulingStrategy.markFailureAttempt()
  }

  private[this] def initialize(res: Reservation, reqStrategy: RequestStrategy, schStrategy: RequestSchedulingStrategy): Unit = {
    if(res.reservationId != id) throw new IllegalArgumentException("id != reservationId")
    if(state != State.NOT_INITIALIZED) throw new IllegalStateException("state != NOT_INITIALIZED")

    persist(RequestInitializedEvent(res, reqStrategy, schStrategy)) { evt =>
      onRequestInitializedEvt(evt)
    }
  }

  private[this] def sendRequest(): Unit = {
    if(state != State.INITIALIZED) throw new IllegalStateException("state != INITIALIZED")

    schedulingStrategy.shouldContinue() match {
      case true => makeRequest()
      case false => terminate()
    }
  }

  private[this] def makeRequest(): Unit = {
    requestStrategy.makeRequest(reservation) match {
      case Success(requestId) => onSuccess(requestId)
      case Failure() => onFailure()
    }
  }

  private[this] def terminate(): Unit = {
    log.info("Request exceeded max attempt amount. Terminating...")
    persist(RequestTerminatedEvent) { evt =>
      system.eventStream.publish(RequestExceedMaxAttemptAmountEvent(reservation))
      onRequestTerminatedEvt()
    }
  }

  private[this] def onSuccess(requestId: String): Unit = {
    log.info("Success request. RequestId: [{}]", requestId)
    persist(RequestFinishedEvent){ evt =>
      system.eventStream.publish(RequestSucceededEvent(requestId, reservation))
      onRequestFinishedEvt()
    }
  }

  private[this] def onFailure(): Unit = {
    log.info("Failure request")
    persist(RequestFailedEvent){ evt => onRequestFailedEvt() }
    schedulingStrategy.schedule
  }

}
