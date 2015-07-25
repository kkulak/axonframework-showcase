package knbit.memberquestions.bc.config;

import knbit.events.bc.common.rabbitmq.HeaderNotificationTagAppender;
import org.springframework.amqp.core.MessagePostProcessor;
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

    public static final String EXCHANGE = "notifications";
    private static final String NOTIFICATION_TYPE = "MEMBER_ASKED_QUESTION";

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
    MessagePostProcessor messagePostProcessor() {
        return new HeaderNotificationTagAppender(NOTIFICATION_TYPE);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                  MessageConverter messageConverter,
                                  MessagePostProcessor messagePostProcessor) {

        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setBeforePublishPostProcessors(messagePostProcessor);
        rabbitTemplate.setExchange(EXCHANGE);
        return rabbitTemplate;
    }


    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
