package knbit.events.bc.common.infrastructure.config;

import knbit.events.bc.common.config.AMQPConstants;
import knbit.events.bc.common.config.BasicRabbitMQConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class RabbitMQConfig extends BasicRabbitMQConfig {

    @Bean
    Queue rsIntegrationInQueue() {
        return new Queue(AMQPConstants.RS_INTEGRATION_IN_QUEUE, false);
    }

    @Bean
    Binding rsIntegrationInBinding() {
        return bind(rsIntegrationInQueue()).to(exchange()).withQueueName();
    }

    @Bean
    Queue rsIntegrationOutQueue() {
        return new Queue(AMQPConstants.RS_INTEGRATION_OUT_QUEUE, false);
    }

    @Bean
    Binding rsIntegrationOutBinding() {
        return bind(rsIntegrationOutQueue()).to(exchange()).withQueueName();
    }

}