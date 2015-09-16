package knbit.rsintegration.bc.scheduling.response

import akka.actor.Scheduler
import knbit.rsintegration.bc.Config

trait ResponseModule {

  def responseSchedulingStrategy(): ResponseSchedulingStrategy = AttemptAmountSchedulingStrategy(scheduler, 10, 100)

  def responseStrategy(): ResponseStrategy = {
    if(Config.ENV == "dev") MockResponseStrategy()
    else throw new NotImplementedError("responseStrategy.prod")
  }

  def scheduler: Scheduler

}
