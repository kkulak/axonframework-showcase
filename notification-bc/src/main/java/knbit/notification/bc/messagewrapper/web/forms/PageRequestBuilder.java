package knbit.notification.bc.messagewrapper.web.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageRequest;

@Getter
@Accessors(fluent = true)
public class PageRequestBuilder {
    private final int start;
    private final int end;

    @JsonCreator
    public PageRequestBuilder(@JsonProperty("start") int start,
                              @JsonProperty("end") int end) {
        Preconditions.checkArgument(end > start, "End less than start!");

        this.start = start;
        this.end = end;
    }

    public PageRequest build() {
        return new PageRequest(start, end);
    }

}
