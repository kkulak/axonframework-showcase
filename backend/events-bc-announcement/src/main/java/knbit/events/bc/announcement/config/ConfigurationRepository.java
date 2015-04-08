package knbit.events.bc.announcement.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by novy on 07.04.15.
 */

// todo: this is only a prototype!
@Repository
public class ConfigurationRepository {

    private static final Map<String, Map<String, String>> properties = Maps.newHashMap();

    static {
        properties.put(Publishers.FACEBOOK, defaultFacebookProperties());
        properties.put(Publishers.TWITTER, defaultTwitterProperties());
        properties.put(Publishers.GOOGLE_GROUP, defaultGoogleGroupProperties());
        properties.put(Publishers.IIET_BOARD, defaultIIETBoardProperties());
    }

    public Map<String, String> findBy(String provider) {
        return properties.get(provider);
    }

    public void update(String publisher, Map<String, String> publisherProperties) {
        properties.put(publisher, publisherProperties);
    }

    private static Map<String, String> defaultFacebookProperties() {
        return ImmutableMap.of(
                "appId", "facebookAppId",
                "appSecret", "facebookAppSecret"
        );
    }

    private static Map<String, String> defaultTwitterProperties() {
        return ImmutableMap.of(
                "consumerKey", "twitterConsumerKey",
                "consumerSecret", "twitterConsumerSecret"
        );
    }

    private static Map<String, String> defaultGoogleGroupProperties() {
        return ImmutableMap.of(
                "username", "username",
                "host", "smpt.google.com",
                "password", "password",
                "googleGroupAddress", "knbittestgroup@googlegroups.com"
        );
    }

    private static Map<String, String> defaultIIETBoardProperties() {
        return ImmutableMap.of(
                "username", "username",
                "password", "password",
                "loginUrl", "http://accounts.iiet.pl/students/sign_in",
                "boardUrl", "https://forum.iiet.pl/",
                "boardId", "57"
        );
    }
}
