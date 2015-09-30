package knbit.rsintegration.bc

import akka.actor.{ActorSystem, Props}
import com.softwaremill.macwire._
import com.thenewmotion.akka.rabbitmq._
import knbit.rsintegration.bc.amqp.AMQP
import knbit.rsintegration.bc.scheduling.{ActorFactory, CrossActorEvent, EventHandler, SchedulingModule}

object Application extends App with SchedulingModule {

  implicit val system = ActorSystem()

  val eventHandler = system.actorOf(Props(wire[EventHandler]))
  system.eventStream.subscribe(eventHandler, classOf[CrossActorEvent])

  val factory = connectionFactory()
  val connection = system.actorOf(ConnectionActor.props(factory), "rabbitmq")
  connection ! CreateChannel(ChannelActor.props(AMQP.setupSubscriber(wire[ActorFactory])), Some("subscriber"))
  connection ! CreateChannel(ChannelActor.props(AMQP.setupPublisher), Some("publisher"))

}
