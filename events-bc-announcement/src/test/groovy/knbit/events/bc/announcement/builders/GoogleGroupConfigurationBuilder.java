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
@NoArgsConstructor(staticName = "newGoogleGroupConfiguration")
public class GoogleGroupConfigurationBuilder {

    private Long id = 666L;
    private String name = "name";
    private boolean isDefault = true;
    private String username = "username";
    private String host = "host";
    private String password = "password";
    private String googleGroupAddress = "address";

    public GoogleGroupConfiguration build() {
        return new GoogleGroupConfiguration(
                id, name, isDefault, username, host, password, googleGroupAddress
        );
    }
}
