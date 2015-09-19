package knbit.events.bc.announcement.twitter.publisher;

import knbit.events.bc.announcement.twitter.configuration.TwitterConfiguration;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Created by novy on 19.09.15.
 */

@Component
public class TwitterPublisherFactory {

    public TwitterPublisher fromConfiguration(TwitterConfiguration configuration) {

        final twitter4j.conf.Configuration twitterFactoryConfiguration = new twitter4j.conf.ConfigurationBuilder()
                .setOAuthConsumerKey(configuration.getConsumerKey())
                .setOAuthConsumerSecret(configuration.getConsumerSecret())
                        // todo: fix
                .setOAuthAccessToken(null)
                .build();

        final Twitter twitter = new TwitterFactory(twitterFactoryConfiguration)
                .getInstance();

        return new TwitterPublisher(twitter);
    }
}
