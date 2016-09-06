package knbit.events.bc.announcement.publishers.googlegroup;

import knbit.events.bc.announcement.AnnouncementException;
import knbit.events.bc.announcement.configuration.googlegroup.GoogleGroupConfiguration;
import knbit.events.bc.announcement.configuration.googlegroup.GoogleGroupConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by novy on 19.09.15.
 */

@Component
public class GoogleGroupPublisherFactory {

    private final GoogleGroupConfigurationRepository repository;

    @Autowired
    public GoogleGroupPublisherFactory(GoogleGroupConfigurationRepository repository) {
        this.repository = repository;
    }

    public GoogleGroupPublisher fromConfigurationBasedOn(Long id) {
        final GoogleGroupConfiguration configuration =
                repository.findOne(id).orElseThrow(this::noSuchGoogleGroupConfiguration);

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

    private AnnouncementException noSuchGoogleGroupConfiguration() {
        return new AnnouncementException("No such googlegroup configuration");
    }
}
