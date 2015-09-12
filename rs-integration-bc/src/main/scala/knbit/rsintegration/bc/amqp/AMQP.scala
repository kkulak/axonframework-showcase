package knbit.rsintegration.bc.amqp

import akka.actor.{ActorRef, ActorSystem}
import com.rabbitmq.client.DefaultConsumer
import com.thenewmotion.akka.rabbitmq._
import knbit.rsintegration.bc.common.Reservation
import knbit.rsintegration.bc.scheduling.ActorFactory

object AMQP {

  val exchange = "amq.direct"
  val queue = "rs.integration.queue"

  def setupSubscriber(factory: ActorFactory)(channel: Channel, self: ActorRef) {
    channel.queueBind(queue, exchange, "")
    val consumer = new DefaultConsumer(channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: Array[Byte]) {
        val reservation = parse[Reservation](body)
        factory.createRequest(reservation)
      }
    }
    channel.basicConsume(queue, true, consumer)
  }
  
}
