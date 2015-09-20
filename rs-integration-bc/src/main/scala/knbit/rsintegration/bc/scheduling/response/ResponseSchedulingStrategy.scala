package knbit.rsintegration.bc.scheduling.response

import akka.actor.{ActorRef, Scheduler}
import scala.language.postfixOps
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext

trait ResponseSchedulingStrategy {
  def markAttempt(): Unit
  def shouldContinue(): Boolean
  def markFailureAttempt(): Unit
  def schedule(scheduler: Scheduler)(implicit ref: ActorRef, ctx: ExecutionContext): Unit
}

case class AttemptAmountSchedulingStrategy(maxFailedAmount: Integer, maxUnresolvedAmount: Integer)
     extends ResponseSchedulingStrategy {
  var currentFailureAmount: Integer = 0
  var currentUnresolvedAmount: Integer = 0

  override def markAttempt(): Unit = currentUnresolvedAmount = currentUnresolvedAmount + 1

  override def markFailureAttempt(): Unit = currentFailureAmount = currentFailureAmount + 1

  override def shouldContinue(): Boolean = currentUnresolvedAmount < maxUnresolvedAmount &&
    currentFailureAmount < maxFailedAmount

  override def schedule(scheduler: Scheduler)(implicit ref: ActorRef, ctx: ExecutionContext): Unit = scheduler.scheduleOnce(5 seconds, ref, CheckResponseCommand)

}