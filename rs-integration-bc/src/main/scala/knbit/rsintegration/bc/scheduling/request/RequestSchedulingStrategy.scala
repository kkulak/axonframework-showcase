package knbit.rsintegration.bc.scheduling.request

import akka.actor.{Scheduler, ActorRef}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

trait RequestSchedulingStrategy {
   def shouldContinue(): Boolean
   def markFailureAttempt(): Unit
   def schedule(scheduler: Scheduler)(implicit ref: ActorRef, ctx: ExecutionContext): Unit
}

case class FailureAttemptAmountSchedulingStrategy(maxAmount: Integer) extends RequestSchedulingStrategy {
  var currentFailureAttempt: Integer = 0

  override def shouldContinue(): Boolean = currentFailureAttempt < maxAmount
  override def markFailureAttempt(): Unit = currentFailureAttempt = currentFailureAttempt + 1
  override def schedule(scheduler: Scheduler)(implicit ref: ActorRef, ctx: ExecutionContext): Unit = scheduler.scheduleOnce(5 seconds, ref, SendRequestCommand)
}
