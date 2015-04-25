package knbit.events.bc.announcement.googlegroup;

import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.AnnouncementException;
import knbit.events.bc.announcement.Publisher;
import lombok.extern.log4j.Log4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by novy on 03.04.15.
 */

@Log4j
public class GoogleGroupPublisher implements Publisher {

    private final String googleGroupEmailAddress;
    private final MailSender sender;

    public GoogleGroupPublisher(String googleGroupEmailAddress, MailSender sender) {
        this.googleGroupEmailAddress = googleGroupEmailAddress;
        this.sender = sender;
    }

    @Override
    public void publish(Announcement announcement) throws AnnouncementException {
        try {
            sender.send(
                    messageFrom(googleGroupEmailAddress, announcement)
            );
        } catch (MailException cause) {
            log.error(cause);
            throw new CannotPostOnGoogleGroupException(cause);
        }

    }

    private SimpleMailMessage messageFrom(String googleGroupAddress, Announcement announcement) {
        final SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(googleGroupAddress);
        message.setSubject(announcement.title());
        message.setText(announcement.content());

        return message;
    }
}
