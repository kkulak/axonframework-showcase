package knbit.rsintegration.bc

import akka.actor.ActorRef
import com.rabbitmq.client.DefaultConsumer
import com.thenewmotion.akka.rabbitmq._
import knbit.rsintegration.utils.parse

object AMQP {

  val exchange = "amq.direct"
  val queue = "rs.integration.queue"

  def setupSubscriber(channel: Channel, self: ActorRef) {
    channel.queueBind(queue, exchange, "")
    val consumer = new DefaultConsumer(channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: Array[Byte]) {
        val reservation = parse[Reservation](body)
        println("received: " + reservation.toString)
      }
    }
    channel.basicConsume(queue, true, consumer)
  }
  
}
