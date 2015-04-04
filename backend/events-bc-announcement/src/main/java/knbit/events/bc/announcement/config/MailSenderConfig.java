package knbit.events.bc.announcement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by novy on 03.04.15.
 */
@Configuration
@PropertySource("classpath:googlegroup.properties")
class MailSenderConfig {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.smtp.auth}")
    private boolean auth;

    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;

    @Bean
    public JavaMailSender mailSender() {
        final JavaMailSenderImpl mailSender= new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.starttls.enable", starttls);

        mailSender.setJavaMailProperties(mailProperties);

        return mailSender;
    }
}
