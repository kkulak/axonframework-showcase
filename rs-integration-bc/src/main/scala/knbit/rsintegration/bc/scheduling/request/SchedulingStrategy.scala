package knbit.rsintegration.bc.scheduling.request

trait SchedulingStrategy {
   def shouldContinue(): Boolean
   def markFailureAttempt(): Unit
}

case class FailureAttemptAmountSchedulingStrategy(maxAmount: Integer) extends SchedulingStrategy {
  var currentFailureAttempt: Integer = 0

  override def shouldContinue(): Boolean = currentFailureAttempt < maxAmount
  override def markFailureAttempt(): Unit = currentFailureAttempt = currentFailureAttempt + 1
}
