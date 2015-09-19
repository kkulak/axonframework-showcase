package knbit.events.bc.announcement.facebook.publisher;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import knbit.events.bc.announcement.AnnouncementException;
import knbit.events.bc.announcement.facebook.configuration.FacebookConfiguration;
import knbit.events.bc.announcement.facebook.configuration.FacebookConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 19.09.15.
 */

@Component
public class FacebookPublisherFactory {

    private final FacebookConfigurationRepository repository;

    @Autowired
    public FacebookPublisherFactory(FacebookConfigurationRepository repository) {
        this.repository = repository;
    }

    public FacebookPublisher fromConfigurationBasedOn(long id) {
        final FacebookConfiguration facebookConfiguration =
                repository.findOne(id).orElseThrow(this::noSuchFacebookConfiguration);

        final facebook4j.conf.Configuration facebookFactoryConfiguration = new facebook4j.conf.ConfigurationBuilder()
                .setOAuthAppId(facebookConfiguration.getAppId())
                .setOAuthAppSecret(facebookConfiguration.getAppSecret())
                .setOAuthAccessToken(null)
                .build();

        final Facebook facebook = new FacebookFactory(facebookFactoryConfiguration)
                .getInstance();

        return new FacebookPublisher(facebook);
    }

    private AnnouncementException noSuchFacebookConfiguration() {
        return new AnnouncementException("No such facebook configuration");
    }

}
