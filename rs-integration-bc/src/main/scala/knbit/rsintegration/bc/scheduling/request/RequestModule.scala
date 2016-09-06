package knbit.rsintegration.bc.scheduling.request

trait RequestModule {

  def requestSchedulingStrategy(): RequestSchedulingStrategy = FailureAttemptAmountSchedulingStrategy(10)

  //  todo: handle profiles
  def requestStrategy(): RequestStrategy = MockRequestStrategy()

}
