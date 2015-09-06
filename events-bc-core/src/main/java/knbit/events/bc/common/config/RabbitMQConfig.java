package knbit.events.bc.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;

import java.util.Map;

import static org.springframework.amqp.core.BindingBuilder.bind;

public class RabbitMQConfig {

    @Bean
    ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(determineSenderIpAddress(), 5672);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setExchange(AMQPConstants.EXCHANGE);
        return rabbitTemplate;
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(AMQPConstants.EXCHANGE);
    }

    @Bean
    Queue rsIntegrationQueue() {
        return new Queue(AMQPConstants.RS_INTEGRATION_QUEUE, false);
    }

    @Bean
    Queue notificationQueue() {
        return new Queue(AMQPConstants.NOTIFICATION_QUEUE, false);
    }

    @Bean
    Binding rsIntegrationBinding() {
        return bind(rsIntegrationQueue()).to(exchange()).withQueueName();
    }

    @Bean
    Binding notificationBinding() {
        return bind(notificationQueue()).to(exchange()).withQueueName();
    }

    private String determineSenderIpAddress() {
        final Map<String, String> environmentVariables = System.getenv();
        return environmentVariables.getOrDefault(AMQPConstants.RABBITMQ_ADDRESS_ENVIRONMENT_VARIABLE, AMQPConstants.DEFAULT_SERVER_IP);
    }

}