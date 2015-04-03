package knbit.events.bc.announcement.config;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.facebook.FacebookPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by novy on 03.04.15.
 */

@Configuration
class FacebookConfig {

    @Bean(name = Publishers.FACEBOOK)
    public Publisher facebookPublisher() {
        final Facebook facebook = FacebookFactory.getSingleton();
        return new FacebookPublisher(facebook);
    }
}
