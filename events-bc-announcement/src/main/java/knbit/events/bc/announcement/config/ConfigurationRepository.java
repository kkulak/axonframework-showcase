package knbit.events.bc.announcement.config;

import knbit.events.bc.announcement.facebook.configuration.FacebookConfiguration;
import knbit.events.bc.announcement.googlegroup.configuration.GoogleGroupConfiguration;
import knbit.events.bc.announcement.iietboard.configuration.IIETBoardConfiguration;
import knbit.events.bc.announcement.twitter.configuration.TwitterConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

/**
 * Created by novy on 07.04.15.
 */

// todo: this is only a prototype!
@Repository
@Setter
@Getter
@Accessors(fluent = true, chain = false)
public class ConfigurationRepository {

    private FacebookConfiguration facebookConfiguration = defaultFacebookProperties();
    private TwitterConfiguration twitterConfiguration = defaultTwitterProperties();
    private GoogleGroupConfiguration googleGroupConfiguration = defaultGoogleGroupProperties();
    private IIETBoardConfiguration iietBoardConfiguration = defaultIIETBoardProperties();

    private FacebookConfiguration defaultFacebookProperties() {
        return new FacebookConfiguration(
                "facebookAppId",
                "facebookAppSecret"
        );
    }

    private TwitterConfiguration defaultTwitterProperties() {
        return new TwitterConfiguration(
                "twitterConsumerKey",
                "twitterConsumerSecret"
        );
    }

    private GoogleGroupConfiguration defaultGoogleGroupProperties() {
        return new GoogleGroupConfiguration(
                "username",
                "smpt.google.com",
                "password",
                "knbittestgroup@googlegroups.com"
        );
    }

    private IIETBoardConfiguration defaultIIETBoardProperties() {
        return new IIETBoardConfiguration(
                "username",
                "password",
                "http://accounts.iiet.pl/students/sign_in",
                "https://forum.iiet.pl/",
                "57"
        );
    }
}
