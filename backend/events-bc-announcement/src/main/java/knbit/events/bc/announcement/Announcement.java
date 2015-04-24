package knbit.events.bc.announcement;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Optional;

/**
 * Created by novy on 02.04.15.
 */

@EqualsAndHashCode
public class Announcement {

    private static final int CONTENT_LENGTH_THRESHOLD = 140;
    private static final UrlValidator urlValidator = UrlValidator.getInstance();

    private final String title;
    private final String content;
    private final String imageUrl;

    public Announcement(String title, String content, String imageUrl) {
        Preconditions.checkNotNull(title);
        Preconditions.checkNotNull(content);
        Preconditions.checkArgument(contentBelowLengthThreshold(content));
        Preconditions.checkArgument(
                Strings.isNullOrEmpty(imageUrl) || hasValidUrlStructure(imageUrl)
        );

        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String title() {
        return title;
    }

    public String content() {
        return content;
    }

    public Optional<String> imageUrl() {
        return Strings.isNullOrEmpty(imageUrl) ? Optional.empty() : Optional.of(imageUrl);
    }

    private boolean hasValidUrlStructure(String imageUrl) {
        return urlValidator.isValid(imageUrl);
    }

    private boolean contentBelowLengthThreshold(String content) {
        return content.length() <= CONTENT_LENGTH_THRESHOLD;
    }


}
