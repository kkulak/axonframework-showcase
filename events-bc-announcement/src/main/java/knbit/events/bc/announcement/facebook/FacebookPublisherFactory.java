package knbit.events.bc.announcement.facebook;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import knbit.events.bc.announcement.facebook.configuration.FacebookConfiguration;
import knbit.events.bc.announcement.facebook.publisher.FacebookPublisher;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 19.09.15.
 */

@Component
public class FacebookPublisherFactory {

    public FacebookPublisher fromConfiguration(FacebookConfiguration facebookConfiguration) {

        final facebook4j.conf.Configuration facebookFactoryConfiguration = new facebook4j.conf.ConfigurationBuilder()
                .setOAuthAppId(facebookConfiguration.getAppId())
                .setOAuthAppSecret(facebookConfiguration.getAppSecret())
                        // todo: fix
                .setOAuthAccessToken(null)
                .build();

        final Facebook facebook = new FacebookFactory(facebookFactoryConfiguration)
                .getInstance();

        return new FacebookPublisher(facebook);
    }
}
