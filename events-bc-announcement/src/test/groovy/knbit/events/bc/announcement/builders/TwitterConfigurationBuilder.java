package knbit.events.bc.announcement.builders;

import knbit.events.bc.announcement.configuration.twitter.TwitterConfiguration;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 12.04.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newTwitterConfiguration")
public class TwitterConfigurationBuilder {

    private Long id = 666L;
    private String name = "name";
    private boolean isDefault = true;
    private String consumerKey = "consumerKey";
    private String consumerSecret = "consumerSecret";

    public TwitterConfiguration build() {
        return new TwitterConfiguration(
                id, name, isDefault, consumerKey, consumerSecret
        );
    }
}
