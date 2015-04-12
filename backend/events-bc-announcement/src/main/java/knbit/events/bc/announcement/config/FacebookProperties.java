package knbit.events.bc.announcement.config;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 11.04.15.
 */

@Value
@Accessors(fluent = true)
public class FacebookProperties {

    private final String appId;
    private final String appSecret;
}

