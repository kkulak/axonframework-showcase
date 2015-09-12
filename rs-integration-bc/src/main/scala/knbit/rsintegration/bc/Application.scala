package knbit.rsintegration.bc

import akka.actor.{ActorSystem, Props}
import com.thenewmotion.akka.rabbitmq._
import knbit.rsintegration.bc.amqp.AMQP
import knbit.rsintegration.bc.scheduling.{ActorFactory, EventHandler, SchedulingModule}
import knbit.rsintegration.bc.scheduling.request.RequestFinishedEvent
import com.softwaremill.macwire._

object Application extends App with SchedulingModule {

  implicit val system = ActorSystem()

  val eventHandler = system.actorOf(Props(wire[EventHandler]))
  system.eventStream.subscribe(eventHandler, classOf[RequestFinishedEvent])

  val factory = new ConnectionFactory()
  val connection = system.actorOf(ConnectionActor.props(factory))
  connection ! CreateChannel(ChannelActor.props(AMQP.setupSubscriber(wire[ActorFactory])))

}
