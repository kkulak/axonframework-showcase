package knbit.notification.bc.proposal.listener;

import knbit.notification.bc.config.RabbitMQConfig;
import knbit.notification.bc.messagewrapper.domain.MessageType;
import knbit.notification.bc.messagewrapper.domain.MessageWrapper;
import knbit.notification.bc.messagewrapper.repository.MessageWrapperRepository;
import knbit.notification.bc.messagewrapper.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 09.05.15.
 */

@Component
@Slf4j
public class ProposalListener {
    private final MessageWrapperRepository messageRepository;
    private final NotificationService notificationService;

    @Autowired
    public ProposalListener(MessageWrapperRepository messageRepository, NotificationService notificationService) {
        this.messageRepository = messageRepository;
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(Message message) {
        log.debug("Received message: " + String.valueOf(message.getBody()));

        final MessageWrapper messageWrapper = messageRepository.save(
                new MessageWrapper(MessageType.EVENT_PROPOSED, String.valueOf(message.getBody()))
        );
        notificationService.publish(messageWrapper);
    }
}
