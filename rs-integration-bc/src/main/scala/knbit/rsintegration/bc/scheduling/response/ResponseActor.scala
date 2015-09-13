package knbit.rsintegration.bc.scheduling.response

import akka.actor.ActorLogging
import akka.persistence.PersistentActor
import knbit.rsintegration.bc.common.{Reservation, Term}
import scala.concurrent.duration._
import scala.language.postfixOps

class ResponseActor(id: String) extends PersistentActor with ActorLogging {
  import context._

  var requestId: String = _
  var reservation: Reservation = _
  var responseStrategy: ResponseStrategy = _
  var schedulingStrategy: ResponseSchedulingStrategy = _

  override def persistenceId: String = s"response-$id"

  override def receiveRecover: Receive = {
    case evt: ResponseInitializedEvent => onResponseInitializedEvt(evt)
    case ResponseFinishedEvent => onResponseFinishedEvt()
    case UnresolvedResponseEvent => onUnresolvedEvt()
    case FailureReservationEvent => onFailureEvt()
  }

  override def receiveCommand: Receive = {
    case cmd: InitializeResponseCommand => handleInitializeCmd(cmd)
    case SendResponseCommand => handleSendResponseCmd()
  }

  private[this] def onResponseInitializedEvt(evt: ResponseInitializedEvent): Unit = {
    this.requestId = evt.requestId
    this.reservation = evt.reservation
    this.responseStrategy = evt.responseStrategy
    this.schedulingStrategy = evt.schedulingStrategy
  }

  private[this] def onResponseFinishedEvt(): Unit = {
    stop(self)
  }

  private[this] def onUnresolvedEvt(): Unit = {
    schedulingStrategy.markAttempt()
  }

  private[this] def onFailureEvt(): Unit = {
    schedulingStrategy.markFailureAttempt()
  }

  private[this] def handleInitializeCmd(cmd: InitializeResponseCommand): Unit = {
    persist(
      ResponseInitializedEvent(cmd.requestId, cmd.reservation,
      cmd.responseStrategy, cmd.schedulingStrategy)
    ){ evt =>
      onResponseInitializedEvt(evt)
      self ! SendResponseCommand
    }
  }

  private[this] def handleSendResponseCmd(): Unit = {
    if(schedulingStrategy.shouldContinue()) {
      responseStrategy.checkResponse(requestId) match {
        case Success(term) => onSuccess(term)
        case Unresolved() => onUnresolved()
        case Rejection() => onRejection()
        case Failure() => onFailure()
      }
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
      system.scheduler.scheduleOnce(5 seconds, self, SendResponseCommand)
    }
  }

  private[this] def onRejection(): Unit = {
    log.info("Rejection response")
    persist(ResponseFinishedEvent){ evt =>
      system.eventStream.publish(FailureReservationEvent(reservation.eventId, reservation.reservationId))
      onResponseFinishedEvt()
    }
  }

  private[this] def onFailure(): Unit = {
    log.info("Failure response")
    persist(FailureReservationEvent){ evt =>
      onFailureEvt()
      system.scheduler.scheduleOnce(5 seconds, self, SendResponseCommand)
    }
  }

}
