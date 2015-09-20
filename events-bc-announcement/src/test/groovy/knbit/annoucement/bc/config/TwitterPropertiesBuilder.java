package knbit.annoucement.bc.config;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 12.04.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newTwitterProperties")
class TwitterPropertiesBuilder {

    private String consumerKey = "consumerKey";
    private String consumerSecret = "consumerSecret";

    public TwitterProperties build() {
        return new TwitterProperties(
                consumerKey, consumerSecret
        );
    }
}
