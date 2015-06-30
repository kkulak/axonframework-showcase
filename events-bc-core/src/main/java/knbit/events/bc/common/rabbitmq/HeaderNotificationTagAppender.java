package knbit.events.bc.common.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;

/**
 * Created by novy on 30.06.15.
 */
public class HeaderNotificationTagAppender implements MessagePostProcessor {

    public static final String NOTIFICATION_TYPE_KEY = "notification-type";
    private final String notificationType;

    public HeaderNotificationTagAppender(String notificationType) {
        this.notificationType = notificationType;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        final MessageProperties messageProperties = message.getMessageProperties();
        messageProperties.setHeader(NOTIFICATION_TYPE_KEY, notificationType);
        return new Message(message.getBody(), messageProperties);
    }
}
