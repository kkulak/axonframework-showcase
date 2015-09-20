package knbit.annoucement.bc.config;

import com.gargoylesoftware.htmlunit.WebClient;
import com.google.common.base.Preconditions;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import knbit.annoucement.bc.publisher.Publisher;
import knbit.annoucement.bc.publisher.facebook.FacebookPublisher;
import knbit.annoucement.bc.publisher.googlegroup.GoogleGroupPublisher;
import knbit.annoucement.bc.publisher.iietboard.BoardPublisherConfiguration;
import knbit.annoucement.bc.publisher.iietboard.IIETBoardPublisher;
import knbit.annoucement.bc.publisher.twitter.TwitterPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by novy on 11.04.15.
 */

@Component
public class PublisherFactory {

    private final ConfigurationRepository configurationRepository;

    @Autowired
    public PublisherFactory(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public Publisher byVendor(Publishers vendor) {
        Preconditions.checkNotNull(vendor);

        switch (vendor) {
            case FACEBOOK:
                return facebookPublisher();
            case TWITTER:
                return twitterPublisher();
            case GOOGLE_GROUP:
                return googleGroupPublisher();
            case IIET_BOARD:
                return iietBoardPublisher();
            default:
                throw new IllegalArgumentException();
        }
    }

    public Collection<Publisher> byVendors(Collection<Publishers> vendors) {
        return vendors
                .stream()
                .map(this::byVendor)
                .collect(Collectors.toList());
    }

    private Publisher iietBoardPublisher() {
        final IIETBoardProperties iietBoardProperties = configurationRepository.iietBoardProperties();

        final BoardPublisherConfiguration configuration = new BoardPublisherConfiguration(
                iietBoardProperties.getUsername(),
                iietBoardProperties.getPassword(),
                iietBoardProperties.getLoginUrl(),
                iietBoardProperties.getBoardUrl(),
                iietBoardProperties.getBoardId()
        );

        final WebClient webClient = new WebClient();

        return new IIETBoardPublisher(
                configuration, webClient
        );
    }

    private Publisher googleGroupPublisher() {
        final GoogleGroupProperties googleGroupProperties = configurationRepository.googleGroupProperties();
        final JavaMailSender mailSender = mailSenderForProps(googleGroupProperties);

        return new GoogleGroupPublisher(
                googleGroupProperties.getGoogleGroupAddress(), mailSender
        );
    }

    private JavaMailSender mailSenderForProps(GoogleGroupProperties properties) {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(properties.getHost());
        mailSender.setUsername(properties.getUsername());
        mailSender.setPassword(properties.getPassword());

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true);

        mailSender.setJavaMailProperties(mailProperties);

        return mailSender;
    }

    private Publisher twitterPublisher() {
        final TwitterProperties twitterProperties = configurationRepository.twitterProperties();

        final twitter4j.conf.Configuration twitterFactoryConfiguration = new twitter4j.conf.ConfigurationBuilder()
                .setOAuthConsumerKey(twitterProperties.getConsumerKey())
                .setOAuthConsumerSecret(twitterProperties.getConsumerSecret())
                        // todo: fix
                .setOAuthAccessToken(null)
                .build();

        final Twitter twitter = new TwitterFactory(twitterFactoryConfiguration)
                .getInstance();

        return new TwitterPublisher(twitter);

    }

    private Publisher facebookPublisher() {
        final FacebookProperties facebookProperties = configurationRepository.facebookProperties();

        final facebook4j.conf.Configuration facebookFactoryConfiguration = new facebook4j.conf.ConfigurationBuilder()
                .setOAuthAppId(facebookProperties.getAppId())
                .setOAuthAppSecret(facebookProperties.getAppSecret())
                        // todo: fix
                .setOAuthAccessToken(null)
                .build();

        final Facebook facebook = new FacebookFactory(facebookFactoryConfiguration)
                .getInstance();

        return new FacebookPublisher(facebook);
    }
}
