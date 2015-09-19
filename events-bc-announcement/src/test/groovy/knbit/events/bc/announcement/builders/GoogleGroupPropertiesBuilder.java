package knbit.events.bc.announcement.builders;

import knbit.events.bc.announcement.googlegroup.configuration.GoogleGroupConfiguration;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 12.04.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newGoogleGroupProperties")
public class GoogleGroupPropertiesBuilder {

    private String username = "username";
    private String host = "host";
    private String password = "password";
    private String googleGroupAddress = "address";

    public GoogleGroupConfiguration build() {
        return new GoogleGroupConfiguration(
                username, host, password, googleGroupAddress
        );
    }
}
