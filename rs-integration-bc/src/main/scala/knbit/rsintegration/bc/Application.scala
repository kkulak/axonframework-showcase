package knbit.rsintegration.bc

import akka.actor.{Props, ActorRef, ActorSystem}
import com.fasterxml.jackson.databind.ObjectMapper
import com.thenewmotion.akka.rabbitmq._
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import knbit.rsintegration.bc.amqp.AMQP
import knbit.rsintegration.bc.scheduling.SchedulerActor

object Application extends App {

  implicit val system = ActorSystem()
  val factory = new ConnectionFactory()
  val connection = system.actorOf(ConnectionActor.props(factory), "rabbitmq")
  val scheduler = system.actorOf(Props[SchedulerActor], "scheduler")
  connection ! CreateChannel(ChannelActor.props(AMQP.setupSubscriber(scheduler)), Some("subscriber-channel"))

}
