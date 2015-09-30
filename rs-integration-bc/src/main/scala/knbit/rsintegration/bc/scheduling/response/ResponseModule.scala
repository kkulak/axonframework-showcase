package knbit.rsintegration.bc.scheduling.response

trait ResponseModule {

  def responseSchedulingStrategy(): ResponseSchedulingStrategy = AttemptAmountSchedulingStrategy(10, 100)

  //  todo: handle app profiles
  def responseStrategy(): ResponseStrategy = MockResponseStrategy()

}
