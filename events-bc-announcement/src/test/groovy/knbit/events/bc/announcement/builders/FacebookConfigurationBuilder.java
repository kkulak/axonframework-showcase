package knbit.events.bc.announcement.builders;

import knbit.events.bc.announcement.facebook.configuration.FacebookConfiguration;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 12.04.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newFacebookConfiguration")
public class FacebookConfigurationBuilder {

    private Long id = 1L;
    private String name = "name";
    private boolean isDefault = true;
    private String appId = "appId";
    private String appSecret = "appSecret";

    public FacebookConfiguration build() {
        return new FacebookConfiguration(
                id, name, isDefault, appId, appSecret
        );
    }
}
