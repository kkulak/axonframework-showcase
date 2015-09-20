package knbit.notification.bc.listener;

import com.google.common.base.Preconditions;
import knbit.events.bc.common.config.AMQPConstants;
import knbit.notification.bc.messagewrapper.domain.MessageWrapper;
import knbit.notification.bc.messagewrapper.infrastructure.dispatcher.NotificationDispatcher;
import knbit.notification.bc.messagewrapper.infrastructure.persistence.MessageWrapperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by novy on 09.05.15.
 */

@Component
@Slf4j
public class MessageListener {

    public static final String NOTIFICATION_TYPE_KEY = "notification-type";

    private final MessageWrapperRepository messageRepository;
    private final NotificationDispatcher notificationDispatcher;

    @Autowired
    public MessageListener(MessageWrapperRepository messageRepository, NotificationDispatcher notificationDispatcher) {
        this.messageRepository = messageRepository;
        this.notificationDispatcher = notificationDispatcher;
    }

    @RabbitListener(queues = AMQPConstants.NOTIFICATION_QUEUE)
    public void receiveMessage(Message message) {
        log.debug("Received message: " + new String(message.getBody()));

        final MessageWrapper messageWrapper = new MessageWrapper(
                extractNotificationTypeFrom(message), new String(message.getBody())
        );
        messageRepository.save(messageWrapper);
        notificationDispatcher.dispatch(messageWrapper);
    }

    private String extractNotificationTypeFrom(Message message) {
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        final String notificationType = (String) headers.get(NOTIFICATION_TYPE_KEY);
        return Preconditions.checkNotNull(
                notificationType,
                "Notification type not specified!"
        );
    }
}
