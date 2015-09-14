package knbit.rsintegration.bc.scheduling.request

import akka.actor.Scheduler
import knbit.rsintegration.bc.Config

trait RequestModule {

  def requestSchedulingStrategy(): RequestSchedulingStrategy = FailureAttemptAmountSchedulingStrategy(scheduler, 10)

  def requestStrategy(): RequestStrategy = {
    if(Config.ENV == "dev") MockRequestStrategy()
    else throw new NotImplementedError("requestStrategy.prod")
  }

  def scheduler: Scheduler

}
