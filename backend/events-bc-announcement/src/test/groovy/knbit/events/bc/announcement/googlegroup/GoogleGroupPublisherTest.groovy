package knbit.events.bc.announcement.googlegroup

import knbit.events.bc.AnnouncementBuilder
import knbit.events.bc.announcement.MessageBuilder
import org.springframework.mail.MailException
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import spock.lang.Specification

/**
 * Created by novy on 03.04.15.
 */
class GoogleGroupPublisherTest extends Specification {

    def "should throw proper domain exception when cannot send mail to google group"() {
        given:
        def announcement = AnnouncementBuilder
                .newAnnouncement()
                .build()

        def googleGroupEmailAddress = "knbit@googlegroup.com"
        def senderMock = Mock(MailSender.class)

        senderMock.send(_ as SimpleMailMessage) >> { throw Mock(MailException.class) }

        def objectUnderTest = new GoogleGroupPublisher(
                googleGroupEmailAddress, senderMock
        )

        when:
        objectUnderTest.publish(announcement)

        then:
        thrown(CannotPostOnGoogleGroupException.class)
    }

    def "should post announcement on google group otherwise"() {
        given:
        def announcement = AnnouncementBuilder
                .newAnnouncement()
                .build()

        def googleGroupEmailAddress = "knbit@googlegroup.com"
        def senderMock = Mock(MailSender.class)
        def objectUnderTest = new GoogleGroupPublisher(
                googleGroupEmailAddress, senderMock
        )

        when:
        objectUnderTest.publish(announcement)

        then:
        1 * senderMock.send(
                MessageBuilder
                        .newMessage()
                        .recipient(googleGroupEmailAddress)
                        .subject(announcement.title())
                        .content(announcement.content())
                        .build()
        )

    }
}
