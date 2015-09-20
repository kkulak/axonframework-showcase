package knbit.events.bc.common.dispatcher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        template.convertAndSend(destinationQueue, message, messagePostProcessor);
    }

}
