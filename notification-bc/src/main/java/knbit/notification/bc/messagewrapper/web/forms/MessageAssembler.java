package knbit.notification.bc.messagewrapper.web.forms;

import knbit.notification.bc.messagewrapper.domain.MessageWrapper;

import java.util.List;
import java.util.stream.Collectors;

public class MessageAssembler {

    public static List<MessageDTO> fromMessageWrappers(List<MessageWrapper> messageWrappers) {
        return messageWrappers
                .stream()
                .map(mw -> MessageDTO.of(
                        mw.getId(), mw.getType(), mw.isRead(), mw.getPayload()
                ))
                .collect(Collectors.toList());
    }

}
