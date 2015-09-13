package knbit.rsintegration.bc.scheduling.request

import akka.actor.ActorLogging
import akka.persistence.PersistentActor
import knbit.rsintegration.bc.common.Reservation

import scala.concurrent.duration._
import scala.language.postfixOps

class RequestActor(id: String) extends PersistentActor with ActorLogging {
  import context._

  var requestStrategy: RequestStrategy = _
  var schedulingStrategy: RequestSchedulingStrategy = _
  var reservation: Reservation = _

  override def persistenceId: String = s"request-$id"

  override def receiveCommand: Receive = {
    case cmd: InitializeRequestCommand => handleInitializeRequestCmd(cmd)
    case SendRequestCommand => handleSendRequestCmd()
  }

  override def receiveRecover: Receive = {
    case evt: RequestInitializedEvent => onRequestInitializedEvt(evt)
    case RequestFinishedEvent => onRequestFinishedEvt()
    case RequestFailedEvent => onRequestFailedEvt()
  }

  private[this] def onRequestInitializedEvt(evt: RequestInitializedEvent): Unit = {
    this.requestStrategy = evt.requestStrategy
    this.schedulingStrategy = evt.schedulingStrategy
    this.reservation = evt.reservation
  }

  private[this] def onRequestFinishedEvt(): Unit = {
    stop(self)
  }

  private[this] def onRequestFailedEvt(): Unit = {
    schedulingStrategy.markFailureAttempt()
  }

  private[this] def handleInitializeRequestCmd(cmd: InitializeRequestCommand): Unit = {
    persist(RequestInitializedEvent(cmd.reservation, cmd.requestStrategy, cmd.schedulingStrategy)) { evt =>
      onRequestInitializedEvt(evt)
      self ! SendRequestCommand
    }
  }

  private[this] def handleSendRequestCmd(): Unit = {
    if(schedulingStrategy.shouldContinue()) {
      requestStrategy.makeRequest(reservation) match {
        case Success(requestId) => onSuccess(requestId)
        case Failure() => onFailure()
      }
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
    persist(RequestFinishedEvent){ evt => onRequestFailedEvt() }
    system.scheduler.scheduleOnce(5 seconds, self, SendRequestCommand)
  }

}
