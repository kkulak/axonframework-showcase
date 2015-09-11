package knbit.rsintegration.bc.scheduling

trait SchedulingStrategy {
   def shouldContinue(): Boolean
   def markAttempt(): Unit
}

case class AttemptAmountSchedulingStrategy(maxAmount: Integer) extends SchedulingStrategy {
  var currentAttempt: Integer = 0

  override def shouldContinue(): Boolean = currentAttempt < maxAmount
  override def markAttempt(): Unit = currentAttempt = currentAttempt + 1
}
