package knbit.notification.bc.messagewrapper.web.forms;

import lombok.Value;

@Value(staticConstructor = "of")
public class MessageDTO {

    private final String id;
    private final String type;
    private final boolean read;
    private final String payload;

}
