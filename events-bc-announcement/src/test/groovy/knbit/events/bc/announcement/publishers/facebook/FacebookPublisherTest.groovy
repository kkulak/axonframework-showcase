package knbit.events.bc.announcement.publishers.facebook

import facebook4j.Facebook
import facebook4j.FacebookException
import facebook4j.PostUpdate
import knbit.events.bc.AnnouncementBuilder
import spock.lang.Specification

/**
 * Created by novy on 03.04.15.
 */
class FacebookPublisherTest extends Specification {

    def "should throw proper domain exception when facebook service fails"() {
        given:
        def announcement = AnnouncementBuilder
                .newAnnouncement()
                .build()

        def facebookMock = Mock(Facebook.class)
        facebookMock.postFeed(_ as PostUpdate) >> { throw new FacebookException("failed") }

        def objectUnderTest = new FacebookPublisher(facebookMock)

        when:
        objectUnderTest.publish(announcement)

        then:
        thrown(CannotPostOnFacebookException.class)
    }

    def "should post announcement content on facebook otherwise"() {
        given:
        def announcement = AnnouncementBuilder
                .newAnnouncement()
                .build()

        def facebookMock = Mock(Facebook.class)
        def objectUnderTest = new FacebookPublisher(facebookMock)

        when:
        objectUnderTest.publish(announcement)

        then:
        1 * facebookMock.postFeed(_ as PostUpdate);
    }
}
