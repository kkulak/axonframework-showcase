package knbit.events.bc.common.dispatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageDispatcher {
    private final RabbitTemplate template;

    @Autowired
    public MessageDispatcher(RabbitTemplate template) {
        this.template = template;
    }

    public void dispatch(Object message, String destinationQueue) {
        template.convertAndSend(destinationQueue, message);
    }

    public void dispatch(Object message, String destinationQueue, String notificationType) {
        final HeaderNotificationTagAppender messagePostProcessor = new HeaderNotificationTagAppender(notificationType);

        log.debug("Sending message to notification-bc: {}", message);
        template.convertAndSend(destinationQueue, message, messagePostProcessor);
    }

}
