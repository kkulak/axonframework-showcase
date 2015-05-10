package knbit.notification.bc.messagewrapper.web;

import knbit.notification.bc.messagewrapper.infrastructure.MessageWrapperRepository;
import knbit.notification.bc.config.Topics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NotificationController {
    private final MessageWrapperRepository messageWrapperRepository;

    @Autowired
    public NotificationController(MessageWrapperRepository messageWrapperRepository) {
        this.messageWrapperRepository = messageWrapperRepository;
    }

    // TODO: separate topic for batch messages
    @MessageMapping("/eventproposal")
    @SendTo(Topics.EVENT_PROPOSED)
    public List<MessageDTO> eventProposals() {
        return messageWrapperRepository
                .findAll()
                .stream()
                .map(mw -> MessageDTO.of(mw.getId(), mw.getType(), mw.getPayload()))
                .collect(Collectors.toList());
    }

}
