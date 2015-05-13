package knbit.notification.bc.messagewrapper.web.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Accessors(fluent = true)
public class PageRequestBuilder {
    private final int page;
    private final int size;

    @JsonCreator
    public PageRequestBuilder(@JsonProperty("page") int page,
                              @JsonProperty("size") int size) {
        this.page = page;
        this.size = size;
    }

    public PageRequest build() {
        return new PageRequest(page, size, Sort.Direction.DESC, "createdOn");
    }

}
