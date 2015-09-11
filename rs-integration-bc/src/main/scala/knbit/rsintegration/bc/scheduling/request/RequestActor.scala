package knbit.rsintegration.bc.scheduling.request

import akka.persistence.PersistentActor
import knbit.rsintegration.bc.common.Reservation
import knbit.rsintegration.bc.scheduling.SchedulingStrategy
import scala.concurrent.duration._
import scala.language.postfixOps

class RequestActor(id: String) extends PersistentActor {
  import context._
  var requestStrategy: RequestStrategy = _
  var schedulingStrategy: SchedulingStrategy = _
  var reservation: Reservation = _

  override def persistenceId: String = s"request-$id"

  override def receiveCommand: Receive = {
    case cmd: InitializeRequestCommand => handleInitializeRequestCommand(cmd)
    case SendRequestCommand() => handleSendRequestCmd()
  }

  override def receiveRecover: Receive = {
    case evt: RequestInitializedEvent => onRequestInitializedEvt(evt)
    case evt: RequestFailedEvent => onRequestFailureEvt(evt)
  }

  private[this] def onRequestInitializedEvt(evt: RequestInitializedEvent): Unit = {
    this.requestStrategy = evt.requestStrategy
    this.schedulingStrategy = evt.schedulingStrategy
    this.reservation = evt.reservation
  }

  private[this] def onRequestFailureEvt(evt: RequestFailedEvent): Unit = {
    schedulingStrategy.markAttempt()
  }

  private[this] def handleInitializeRequestCommand(cmd: InitializeRequestCommand): Unit = {
    persist(RequestInitializedEvent(cmd.reservation, cmd.requestStrategy, cmd.schedulingStrategy)) { evt =>
      onRequestInitializedEvt(evt)
      system.scheduler.scheduleOnce(0 seconds, self, SendRequestCommand())
    }
  }

  private[this] def handleSendRequestCmd(): Unit = {
    if(schedulingStrategy.shouldContinue()) {
      requestStrategy.makeRequest(reservation) match {
        case Success(requestId) => println("success: implement me!")
        case Failure() => onRequestFailure()
      }
    }
  }

  private[this] def onRequestFailure(): Unit = {
    persist(RequestFailedEvent())(evt => onRequestFailureEvt(evt))
    system.scheduler.scheduleOnce(30 seconds, self, SendRequestCommand())
  }

}
