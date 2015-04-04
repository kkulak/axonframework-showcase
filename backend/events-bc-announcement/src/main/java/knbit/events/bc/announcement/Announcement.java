package knbit.events.bc.announcement;

import com.google.common.base.Preconditions;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 02.04.15.
 */

@Accessors(fluent = true)
@Value
public class Announcement {

    private final String title;
    private final String content;

    public Announcement(String title, String content) {
        Preconditions.checkNotNull(title);
        Preconditions.checkNotNull(content);

        this.title = title;
        this.content = content;
    }
}
