package knbit.events.bc.announcement.googlegroup.publisher;

import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.AnnouncementException;
import knbit.events.bc.announcement.Publisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by novy on 03.04.15.
 */

@Slf4j
public class GoogleGroupPublisher implements Publisher {

    private final String googleGroupEmailAddress;
    private final JavaMailSender sender;

    public GoogleGroupPublisher(String googleGroupEmailAddress, JavaMailSender sender) {
        this.googleGroupEmailAddress = googleGroupEmailAddress;
        this.sender = sender;
    }

    @Override
    public void publish(Announcement announcement) throws AnnouncementException {
        try {
            sender.send(
                    messageFrom(googleGroupEmailAddress, announcement)
            );
        } catch (MailException | MessagingException | IOException cause) {
            log.error("CannotPostOnGoogleGroup", cause);
            throw new CannotPostOnGoogleGroupException(cause);
        }

    }

    private MimeMessage messageFrom(String googleGroupAddress, Announcement announcement) throws MessagingException, MalformedURLException {
        final MimeMessage message = sender.createMimeMessage();

        final boolean multipart = true;
        final MimeMessageHelper messageHelper = new MimeMessageHelper(message, multipart);

        messageHelper.setTo(googleGroupAddress);
        messageHelper.setSubject(announcement.title());
        messageHelper.setText(announcement.content());

        final Optional<String> possibleImageUrl = announcement.imageUrl();
        if (possibleImageUrl.isPresent()) {

            final String imageUrlString = possibleImageUrl.get();
            final String imageName = announcement.imageName().get();
            messageHelper.addAttachment(
                    imageName, createUrlResourceFrom(imageUrlString)
            );

        }

        return message;
    }

    private UrlResource createUrlResourceFrom(String imageUrlString) throws MalformedURLException {
        return new UrlResource(
                new URL(imageUrlString)
        );

    }
}
