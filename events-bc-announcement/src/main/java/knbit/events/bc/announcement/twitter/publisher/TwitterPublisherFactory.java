package knbit.events.bc.announcement.twitter.publisher;

import knbit.events.bc.announcement.AnnouncementException;
import knbit.events.bc.announcement.twitter.configuration.TwitterConfiguration;
import knbit.events.bc.announcement.twitter.configuration.TwitterConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Created by novy on 19.09.15.
 */

@Component
public class TwitterPublisherFactory {

    private final TwitterConfigurationRepository repository;

    @Autowired
    public TwitterPublisherFactory(TwitterConfigurationRepository repository) {
        this.repository = repository;
    }

    public TwitterPublisher fromConfigurationBasedOn(Long id) {
        final TwitterConfiguration configuration =
                repository.findOne(id).orElseThrow(this::noSuchTwitterConfiguration);

        final twitter4j.conf.Configuration twitterFactoryConfiguration = new twitter4j.conf.ConfigurationBuilder()
                .setOAuthConsumerKey(configuration.getConsumerKey())
                .setOAuthConsumerSecret(configuration.getConsumerSecret())
                .setOAuthAccessToken(null)
                .build();

        final Twitter twitter = new TwitterFactory(twitterFactoryConfiguration)
                .getInstance();

        return new TwitterPublisher(twitter);
    }

    private AnnouncementException noSuchTwitterConfiguration() {
        return new AnnouncementException("No such twitter configuration");
    }
}
