package knbit.notification.bc.messagewrapper.web;

import knbit.notification.bc.config.Endpoint;
import knbit.notification.bc.config.Topic;
import knbit.notification.bc.messagewrapper.domain.dispatcherpolicy.DispatcherPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NotificationController {
    private final DispatcherPolicy dispatcherPolicy;

    @Autowired
    public NotificationController(DispatcherPolicy dispatcherPolicy) {
        this.dispatcherPolicy = dispatcherPolicy;
    }

    @MessageMapping(Endpoint.INITIAL)
    @SendTo(Topic.INITIAL)
    public List<MessageDTO> initialMessages() {
        return dispatcherPolicy
                .oldMessages()
                .stream()
                .map(mw -> MessageDTO.of(mw.getId(), mw.getType(), mw.getPayload()))
                .collect(Collectors.toList());
    }

}
