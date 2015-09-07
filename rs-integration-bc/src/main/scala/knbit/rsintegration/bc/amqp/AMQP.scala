package knbit.rsintegration.bc.amqp

import akka.actor.ActorRef
import com.rabbitmq.client.DefaultConsumer
import com.thenewmotion.akka.rabbitmq._
import knbit.rsintegration.bc.scheduling.ScheduleRoomReservationRequestCommand

object AMQP {

  val exchange = "amq.direct"
  val queue = "rs.integration.queue"

  def setupSubscriber(scheduler: ActorRef)(channel: Channel, self: ActorRef) {
    channel.queueBind(queue, exchange, "")
    val consumer = new DefaultConsumer(channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: Array[Byte]) {
        val command = parse[ScheduleRoomReservationRequestCommand](body)
        scheduler ! command
      }
    }
    channel.basicConsume(queue, true, consumer)
  }
  
}
