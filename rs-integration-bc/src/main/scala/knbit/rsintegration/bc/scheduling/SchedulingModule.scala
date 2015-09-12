package knbit.rsintegration.bc.scheduling

import akka.actor.ActorSystem
import knbit.rsintegration.bc.scheduling.request.RequestModule
import knbit.rsintegration.bc.scheduling.response.ResponseModule
import com.softwaremill.macwire._

trait SchedulingModule extends RequestModule with ResponseModule {
  lazy val actorFactory: ActorFactory = wire[ActorFactory]

  def system: ActorSystem
}
