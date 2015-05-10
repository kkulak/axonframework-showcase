package knbit.notification.bc.messagewrapper.web;

import knbit.notification.bc.messagewrapper.domain.MessageType;
import lombok.Value;

@Value(staticConstructor = "of")
public class MessageDTO {

    private final String id;
    private final MessageType type;
    private final String payload;

}
