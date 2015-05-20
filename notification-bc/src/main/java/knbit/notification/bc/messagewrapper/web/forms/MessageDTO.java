package knbit.notification.bc.messagewrapper.web.forms;

import knbit.notification.bc.messagewrapper.domain.MessageType;
import lombok.Value;

@Value(staticConstructor = "of")
public class MessageDTO {

    private final String id;
    private final MessageType type;
    private final boolean read;
    private final String payload;

}
