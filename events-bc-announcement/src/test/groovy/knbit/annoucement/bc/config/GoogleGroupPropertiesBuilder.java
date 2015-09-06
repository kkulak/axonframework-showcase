package knbit.annoucement.bc.config;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 12.04.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newGoogleGroupProperties")
class GoogleGroupPropertiesBuilder {

    private String username = "username";
    private String host = "host";
    private String password = "password";
    private String googleGroupAddress = "address";

    public GoogleGroupProperties build() {
        return new GoogleGroupProperties(
                username, host, password, googleGroupAddress
        );
    }
}
