package knbit.rsintegration.bc.amqp

import com.rabbitmq.client.ConnectionFactory
import knbit.rsintegration.bc.Config

/**
 * Created by novy on 30.09.15.
 */
trait ConnectionModule {

  def connectionFactory(): ConnectionFactory = {
    val factory: ConnectionFactory = new ConnectionFactory()
    factory.setHost(host())

    factory
  }

  private def host(): String = {
    Config.ENV match {
      case "prod" => "rabbitmq"
      case _ => "localhost"
    }
  }
}
