package knbit.annoucement.bc.config;

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

    private FacebookProperties facebookProperties = defaultFacebookProperties();
    private TwitterProperties twitterProperties = defaultTwitterProperties();
    private GoogleGroupProperties googleGroupProperties = defaultGoogleGroupProperties();
    private IIETBoardProperties iietBoardProperties = defaultIIETBoardProperties();

    private FacebookProperties defaultFacebookProperties() {
        return new FacebookProperties(
                "facebookAppId",
                "facebookAppSecret"
        );
    }

    private TwitterProperties defaultTwitterProperties() {
        return new TwitterProperties(
                "twitterConsumerKey",
                "twitterConsumerSecret"
        );
    }

    private GoogleGroupProperties defaultGoogleGroupProperties() {
        return new GoogleGroupProperties(
                "username",
                "smpt.google.com",
                "password",
                "knbittestgroup@googlegroups.com"
        );
    }

    private IIETBoardProperties defaultIIETBoardProperties() {
        return new IIETBoardProperties(
                "username",
                "password",
                "http://accounts.iiet.pl/students/sign_in",
                "https://forum.iiet.pl/",
                "57"
        );
    }
}
