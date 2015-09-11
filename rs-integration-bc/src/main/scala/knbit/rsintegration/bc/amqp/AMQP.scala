package knbit.rsintegration.bc.amqp

import akka.actor.{Props, ActorSystem, ActorRef}
import com.rabbitmq.client.DefaultConsumer
import com.thenewmotion.akka.rabbitmq._
import knbit.rsintegration.bc.common.Reservation
import knbit.rsintegration.bc.scheduling.AttemptAmountSchedulingStrategy
import knbit.rsintegration.bc.scheduling.request._

object AMQP {

  val exchange = "amq.direct"
  val queue = "rs.integration.queue"

  def setupSubscriber(channel: Channel, self: ActorRef)(implicit system: ActorSystem) {
    channel.queueBind(queue, exchange, "")
    val consumer = new DefaultConsumer(channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: Array[Byte]) {
        val reservation = parse[Reservation](body)
        val requestActor = system.actorOf(
          Props(classOf[RequestActor],
          reservation.reservationId)
        )
        requestActor ! InitializeRequestCommand(
          reservation, MockRequestStrategy(), AttemptAmountSchedulingStrategy(10)
        )
      }
    }
    channel.basicConsume(queue, true, consumer)
  }
  
}
