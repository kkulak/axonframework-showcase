package knbit.events.bc.announcement.publishers.googlegroup

import knbit.events.bc.announcement.builders.AnnouncementBuilder
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification

import javax.mail.Session
import javax.mail.internet.MimeMessage

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
        def senderMock = Stub(JavaMailSender.class)

        senderMock.send(_ as MimeMessage) >> { throw Mock(MailException.class) }

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
        def senderMock = Mock(JavaMailSender.class)
        senderMock.createMimeMessage() >> new MimeMessage(null as Session)

        def objectUnderTest = new GoogleGroupPublisher(
                googleGroupEmailAddress, senderMock
        )

        when:
        objectUnderTest.publish(announcement)

        then:
        1 * senderMock.send(_ as MimeMessage)

    }
}
