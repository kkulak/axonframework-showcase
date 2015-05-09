package knbit.notification.bc.messagewrapper.web;

import lombok.Value;

@Value(staticConstructor = "of")
public class MessageDTO {

    private final String id;
    private final String payload;

}
