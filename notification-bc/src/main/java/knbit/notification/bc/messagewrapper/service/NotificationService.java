package knbit.notification.bc.messagewrapper.service;

import knbit.notification.bc.messagewrapper.domain.MessageWrapper;
import knbit.notification.bc.messagewrapper.web.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(MessageWrapper message) {
        final String topic = TopicMatcher.match(message.getType());
        final MessageDTO messageDTO = MessageDTO.of(
                message.getId(), message.getPayload()
        );
        messagingTemplate.convertAndSend(
                topic, messageDTO
        );
    }

}
