package knbit.annoucement.bc.config;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 12.04.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newFacebookProperties")
class FacebookPropertiesBuilder {

    private String appId = "appId";
    private String appSecret = "appSecret";

    public FacebookProperties build() {
        return new FacebookProperties(
                appId, appSecret
        );
    }
}
