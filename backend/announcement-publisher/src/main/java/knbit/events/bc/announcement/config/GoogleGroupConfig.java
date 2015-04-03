package knbit.events.bc.announcement.config;

import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.googlegroup.GoogleGroupPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Created by novy on 03.04.15.
 */

@Configuration
class GoogleGroupConfig {

    private final String googleGroupAddress = "knbittestgroup@googlegroups.com";

    @Bean
    @Autowired
    public Publisher googleGroupPublisher(JavaMailSender mailSender) {
        return new GoogleGroupPublisher(
                googleGroupAddress, mailSender
        );
    }
}
