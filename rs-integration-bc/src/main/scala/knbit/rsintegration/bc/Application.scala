package knbit.rsintegration.bc

import akka.actor.{ActorSystem, Props}
import com.softwaremill.macwire._
import com.thenewmotion.akka.rabbitmq._
import knbit.rsintegration.bc.amqp.AMQP
import knbit.rsintegration.bc.scheduling.{ActorFactory, EventHandler, SchedulingModule}
import scala.concurrent.duration._

object Application extends App with SchedulingModule {

  implicit val system = ActorSystem()

  val eventHandler = system.actorOf(Props(wire[EventHandler]))
  system.eventStream.subscribe(eventHandler, classOf[AnyRef])

  val factory = new ConnectionFactory()
  val connection = system.actorOf(ConnectionActor.props(factory), "rabbitmq")
  connection ! CreateChannel(ChannelActor.props(AMQP.setupSubscriber(wire[ActorFactory])), Some("subscriber"))
  connection ! CreateChannel(ChannelActor.props(AMQP.setupPublisher), Some("publisher"))

}
