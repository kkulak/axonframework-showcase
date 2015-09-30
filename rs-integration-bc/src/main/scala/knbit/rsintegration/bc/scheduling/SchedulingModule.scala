package knbit.rsintegration.bc.scheduling

import akka.actor.ActorSystem
import com.softwaremill.macwire._
import knbit.rsintegration.bc.amqp.ConnectionModule
import knbit.rsintegration.bc.scheduling.request.RequestModule
import knbit.rsintegration.bc.scheduling.response.ResponseModule

trait SchedulingModule extends RequestModule with ResponseModule with ConnectionModule {
  lazy val actorFactory: ActorFactory = wire[ActorFactory]

  def system: ActorSystem

}
