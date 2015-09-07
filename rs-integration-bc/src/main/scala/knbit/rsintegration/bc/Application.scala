package knbit.rsintegration.bc

import akka.actor.{ActorRef, ActorSystem}
import com.fasterxml.jackson.databind.ObjectMapper
import com.thenewmotion.akka.rabbitmq._
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object Application extends App {

  implicit val system = ActorSystem()
  val factory = new ConnectionFactory()
  val connection = system.actorOf(ConnectionActor.props(factory), "rabbitmq")
  connection ! CreateChannel(ChannelActor.props(AMQP.setupSubscriber), Some("subscriber-channel"))

}
