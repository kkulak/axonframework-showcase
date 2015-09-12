package knbit.rsintegration.bc.scheduling.response

trait ResponseSchedulingStrategy {
  def markAttempt(): Unit
  def shouldContinue(): Boolean
  def markFailureAttempt(): Unit
}

case class AttemptAmountSchedulingStrategy(maxFailedAmount: Integer, maxUnresolvedAmount: Integer)
     extends ResponseSchedulingStrategy {
  var currentFailureAmount: Integer = 0
  var currentUnresolvedAmount: Integer = 0

  override def markAttempt(): Unit = currentUnresolvedAmount = currentUnresolvedAmount + 1

  override def markFailureAttempt(): Unit = currentFailureAmount = currentFailureAmount + 1

  override def shouldContinue(): Boolean = currentUnresolvedAmount < maxUnresolvedAmount &&
    currentFailureAmount < maxFailedAmount

}