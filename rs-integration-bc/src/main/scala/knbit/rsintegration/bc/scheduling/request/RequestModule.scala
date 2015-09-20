package knbit.rsintegration.bc.scheduling.request

import knbit.rsintegration.bc.Config

trait RequestModule {

  def requestSchedulingStrategy(): RequestSchedulingStrategy = FailureAttemptAmountSchedulingStrategy(10)

  def requestStrategy(): RequestStrategy = {
    if(Config.ENV == "dev") MockRequestStrategy()
    else throw new NotImplementedError("requestStrategy.prod")
  }

}
