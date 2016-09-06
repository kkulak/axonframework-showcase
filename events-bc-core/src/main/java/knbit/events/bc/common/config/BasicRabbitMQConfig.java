package knbit.events.bc.common.config;

import knbit.events.bc.common.Profiles;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import static org.springframework.amqp.core.BindingBuilder.bind;

@EnableRabbit
public class BasicRabbitMQConfig {

    @Profile(Profiles.PROD)
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(AMQPConstants.RABBITMQ_SERVER_HOST, AMQPConstants.RABBITMQ_SERVER_PORT);
    }

    @Profile("!production")
    @Bean
    public ConnectionFactory localConnectionFactory() {
        return new CachingConnectionFactory(AMQPConstants.DEFAULT_SERVER_HOST, AMQPConstants.RABBITMQ_SERVER_PORT);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setExchange(AMQPConstants.EXCHANGE);
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(AMQPConstants.EXCHANGE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(AMQPConstants.NOTIFICATION_QUEUE, false);
    }

    @Bean
    public Binding notificationBinding() {
        return bind(notificationQueue()).to(exchange()).withQueueName();
    }
}