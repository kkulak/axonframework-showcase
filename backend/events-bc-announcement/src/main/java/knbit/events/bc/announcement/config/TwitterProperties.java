package knbit.events.bc.announcement.config;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 11.04.15.
 */

@Value
@Accessors(fluent = true)
public class TwitterProperties {

    private final String consumerKey;
    private final String consumerSecret;
}
