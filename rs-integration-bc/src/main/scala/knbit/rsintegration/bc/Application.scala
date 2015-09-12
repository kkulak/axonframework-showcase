package knbit.rsintegration.bc

import akka.actor.{ActorSystem, Props}
import com.thenewmotion.akka.rabbitmq._
import knbit.rsintegration.bc.amqp.AMQP
import knbit.rsintegration.bc.scheduling.EventHandlerActor
import knbit.rsintegration.bc.scheduling.request.RequestFinishedEvent

object Application extends App {

  implicit val system = ActorSystem()

  val eventHandler = system.actorOf(Props[EventHandlerActor], "event-handler")
  system.eventStream.subscribe(eventHandler, classOf[RequestFinishedEvent])

  val factory = new ConnectionFactory()
  val connection = system.actorOf(ConnectionActor.props(factory), "rabbitmq")
  connection ! CreateChannel(ChannelActor.props(AMQP.setupSubscriber), Some("subscriber-channel"))

}
