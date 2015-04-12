package knbit.events.bc.announcement.config;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 11.04.15.
 */

@Value
@Accessors(fluent = true)
public class GoogleGroupProperties {

    private final String username;
    private final String host;
    private final String password;
    private final String googleGroupAddress;
}
