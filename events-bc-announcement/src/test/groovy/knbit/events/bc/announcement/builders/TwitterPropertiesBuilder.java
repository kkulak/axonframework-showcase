package knbit.events.bc.announcement.builders;

import knbit.events.bc.announcement.twitter.configuration.TwitterConfiguration;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 12.04.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newTwitterProperties")
public class TwitterPropertiesBuilder {

    private String consumerKey = "consumerKey";
    private String consumerSecret = "consumerSecret";

    public TwitterConfiguration build() {
        return new TwitterConfiguration(
                consumerKey, consumerSecret
        );
    }
}
