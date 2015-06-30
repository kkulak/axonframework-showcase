package knbit.events.bc.eventproposal.notificationdispatcher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 09.05.15.
 */

@Component
public class ProposalNotificationDispatcher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProposalNotificationDispatcher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void dispatch(ProposalNotification notification) {
        rabbitTemplate.convertAndSend(notification);
    }
}
