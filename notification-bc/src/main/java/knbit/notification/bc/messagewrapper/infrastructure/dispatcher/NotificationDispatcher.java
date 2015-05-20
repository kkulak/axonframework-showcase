package knbit.notification.bc.messagewrapper.infrastructure.dispatcher;

import knbit.notification.bc.config.Topic;
import knbit.notification.bc.messagewrapper.domain.MessageWrapper;
import knbit.notification.bc.messagewrapper.web.forms.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationDispatcher {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationDispatcher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void dispatch(MessageWrapper message) {
        final MessageDTO messageDTO = MessageDTO.of(
                message.getId(), message.getType(), message.isRead(), message.getPayload()
        );
        messagingTemplate.convertAndSend(
                Topic.CURRENT, messageDTO
        );
    }

}
