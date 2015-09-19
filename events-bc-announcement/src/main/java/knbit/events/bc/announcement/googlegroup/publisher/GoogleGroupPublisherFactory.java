package knbit.events.bc.announcement.googlegroup.publisher;

import knbit.events.bc.announcement.googlegroup.configuration.GoogleGroupConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by novy on 19.09.15.
 */

@Component
public class GoogleGroupPublisherFactory {

    public GoogleGroupPublisher fromConfiguration(GoogleGroupConfiguration configuration) {
        final JavaMailSender mailSender = mailSenderForProps(configuration);

        return new GoogleGroupPublisher(
                configuration.getGoogleGroupAddress(), mailSender
        );
    }

    private JavaMailSender mailSenderForProps(GoogleGroupConfiguration properties) {
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
}
