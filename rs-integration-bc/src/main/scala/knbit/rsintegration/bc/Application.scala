package knbit.rsintegration.bc

import akka.actor.ActorSystem
import com.thenewmotion.akka.rabbitmq._
import knbit.rsintegration.bc.amqp.AMQP

object Application extends App {

  implicit val system = ActorSystem()
  val factory = new ConnectionFactory()
  val connection = system.actorOf(ConnectionActor.props(factory), "rabbitmq")
  connection ! CreateChannel(ChannelActor.props(AMQP.setupSubscriber), Some("subscriber-channel"))

}
