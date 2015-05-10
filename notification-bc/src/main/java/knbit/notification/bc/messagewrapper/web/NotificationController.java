package knbit.notification.bc.messagewrapper.web;

import com.google.common.base.Preconditions;
import knbit.notification.bc.config.Endpoint;
import knbit.notification.bc.config.Topic;
import knbit.notification.bc.messagewrapper.domain.MessageWrapper;
import knbit.notification.bc.messagewrapper.domain.dispatcherpolicy.DispatcherPolicy;
import knbit.notification.bc.messagewrapper.infrastructure.persistence.MessageWrapperRepository;
import knbit.notification.bc.messagewrapper.web.forms.MessageAssembler;
import knbit.notification.bc.messagewrapper.web.forms.MessageDTO;
import knbit.notification.bc.messagewrapper.web.forms.MessageIdentity;
import knbit.notification.bc.messagewrapper.web.forms.PageRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NotificationController {
    private final DispatcherPolicy dispatcherPolicy;
    private final MessageWrapperRepository messageRepository;

    @Autowired
    public NotificationController(DispatcherPolicy dispatcherPolicy, MessageWrapperRepository messageRepository) {
        this.dispatcherPolicy = dispatcherPolicy;
        this.messageRepository = messageRepository;
    }

    @MessageMapping(Endpoint.INITIAL)
    @SendTo(Topic.INITIAL)
    public List<MessageDTO> initialMessages() {
        final List<MessageWrapper> messageWrappers = dispatcherPolicy.oldMessages();
        return MessageAssembler.fromMessageWrappers(
                messageWrappers
        );
    }

    @MessageMapping(Endpoint.BATCH)
    @SendTo(Topic.BATCH)
    public List<MessageDTO> batchMessages(PageRequestBuilder builder) {
        final List<MessageWrapper> messageWrappers = messageRepository.findAll(builder.build())
                .getContent();
        return MessageAssembler.fromMessageWrappers(
                messageWrappers
        );
    }

    @MessageMapping(Endpoint.MESSAGE_STATE)
    @SendTo(Topic.MESSAGE_STATE)
    public MessageWrapper markRead(MessageIdentity identity) {
        final MessageWrapper message = messageRepository.findOne(identity.id());
        Preconditions.checkArgument(message != null, "Given message does not exist!");
        message.markRead();
        return messageRepository.save(message);
    }

}
