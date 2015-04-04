package knbit.events.bc.announcement.config;

import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.twitter.TwitterPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Created by novy on 03.04.15.
 */

@Configuration
class TwitterConfig {

    @Bean(name = Publishers.TWITTER)
    public Publisher twitterPublisher() {
        final Twitter twitter = TwitterFactory.getSingleton();
        return new TwitterPublisher(twitter);
    }
}
