package knbit.notification.bc.proposal.listener;

import knbit.notification.bc.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 09.05.15.
 */

@Component
public class ProposalListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(Message message) {
        System.out.println(
                "Received <" + new String(message.getBody()) + ">"
        );
    }
}
