package knbit.notification.bc.messagewrapper.web.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class MessageIdentity {
    private final String id;

    @JsonCreator
    public MessageIdentity(@JsonProperty("id") String id) {
        this.id = id;
    }

}
