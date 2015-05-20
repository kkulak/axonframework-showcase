package knbit.events.bc.announcement.twitter

import knbit.events.bc.AnnouncementBuilder
import knbit.events.bc.announcement.twitter.TwitterPublisher.ImageStreamReader
import spock.lang.Specification
import twitter4j.StatusUpdate
import twitter4j.Twitter
import twitter4j.TwitterException

/**
 * Created by novy on 03.04.15.
 */
class TwitterPublisherTest extends Specification {

    def "should throw proper domain exception when twitter service fails"() {
        given:
        def announcement = AnnouncementBuilder
                .newAnnouncement()
                .build()

        def twitterMock = Mock(Twitter.class)
        twitterMock.updateStatus(_ as StatusUpdate) >> { throw new TwitterException("failed") }

        def objectUnderTest = new TwitterPublisher(twitterMock)

        when:
        objectUnderTest.publish(announcement)

        then:
        thrown(CannotPostOnTwitterException.class)
    }

    def "should post announcement content on twitter otherwise"() {
        given:
        def announcement = AnnouncementBuilder
                .newAnnouncement()
                .build()

        def twitterMock = Mock(Twitter.class)
        def imageStreamReaderMock = Mock(ImageStreamReader.class)
        imageStreamReaderMock.createImageStreamFrom(_ as String) >> new ByteArrayInputStream("url".getBytes())

        def objectUnderTest = new TwitterPublisher(twitterMock, imageStreamReaderMock)

        when:
        objectUnderTest.publish(announcement)

        then:
        1 * twitterMock.updateStatus(_ as StatusUpdate)
    }
}
