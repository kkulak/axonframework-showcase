package knbit.events.bc.announcement.publishers.iietboard

import com.gargoylesoftware.htmlunit.WebClient
import knbit.events.bc.AnnouncementBuilder
import spock.lang.Specification

/**
 * Created by novy on 05.04.15.
 */
class IIETBoardPublisherTest extends Specification {

    def "should throw domain exception when web client fails"() {

        given:
        def webClientMock = Mock(WebClient.class)
        webClientMock.getPage(_) >> { throw new IOException() }

        def objectUnderTest = new IIETBoardPublisher(
                Mock(BoardPublisherConfiguration.class), webClientMock
        )

        def announcement = AnnouncementBuilder
                .newAnnouncement()
                .build()

        when:
        objectUnderTest.publish(announcement)

        then:
        thrown(CannotPostOnBoardException.class)
    }
}
