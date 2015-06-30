package knbit.notification.bc.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by novy on 09.05.15.
 */

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "knbit-events-bc";
    private static final String PROPOSAL_TOPIC_EXCHANGE = "proposal-notification";
    private static final String MEMBER_MESSAGES_TOPIC_EXCHANGE = "member_messages";

    private static final int RABBITMQ_SERVER_PORT = 5672;
    private static final String RABBITMQ_ADDRESS_ENVIRONMENT_VARIABLE = "RABBITMQ_PORT_" + RABBITMQ_SERVER_PORT + "_TCP_ADDR";
    private static final String DEFAULT_SERVER_IP = "127.0.0.1";

    @Bean
    ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(determineSenderIpAddress(), 5672);
    }

    private String determineSenderIpAddress() {
        final Map<String, String> environmentVariables = System.getenv();
        return environmentVariables.getOrDefault(RABBITMQ_ADDRESS_ENVIRONMENT_VARIABLE, DEFAULT_SERVER_IP);
    }


    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    Queue queue() {
        final boolean durable = false;
        return new Queue(QUEUE_NAME, durable);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(PROPOSAL_TOPIC_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("");
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

