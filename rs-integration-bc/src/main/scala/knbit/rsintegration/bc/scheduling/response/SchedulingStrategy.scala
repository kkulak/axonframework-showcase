package knbit.rsintegration.bc.scheduling.response

trait SchedulingStrategy extends knbit.rsintegration.bc.scheduling.request.SchedulingStrategy {
  def markAttempt(): Unit
}

case class AttemptAmountSchedulingStrategy(maxFailedAmount: Integer, maxUnresolvedAmount: Integer)
     extends SchedulingStrategy {
  var currentFailureAmount: Integer = 0
  var currentUnresolvedAmount: Integer = 0

  override def markAttempt(): Unit = currentUnresolvedAmount = currentUnresolvedAmount + 1

  override def markFailureAttempt(): Unit = currentFailureAmount = currentFailureAmount + 1

  override def shouldContinue(): Boolean = currentUnresolvedAmount < maxUnresolvedAmount &&
    currentFailureAmount < maxFailedAmount

}



