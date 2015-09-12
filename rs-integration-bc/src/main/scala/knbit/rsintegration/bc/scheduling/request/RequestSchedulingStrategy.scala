package knbit.rsintegration.bc.scheduling.request

trait RequestSchedulingStrategy {
   def shouldContinue(): Boolean
   def markFailureAttempt(): Unit
}

case class FailureAttemptAmountSchedulingStrategy(maxAmount: Integer) extends RequestSchedulingStrategy {
  var currentFailureAttempt: Integer = 0

  override def shouldContinue(): Boolean = currentFailureAttempt < maxAmount
  override def markFailureAttempt(): Unit = currentFailureAttempt = currentFailureAttempt + 1
}
