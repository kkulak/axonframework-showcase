package knbit.events.bc.announcement.coordinator

import knbit.events.bc.AnnouncementBuilder
import knbit.events.bc.announcement.Publisher
import spock.lang.Specification

/**
 * Created by novy on 02.04.15.
 */
class PublisherCoordinatorTest extends Specification {

    def "should thrown an exception when no publisher specified"() {
        given:
        def objectUnderTest = new PublisherCoordinator();

        when:
        objectUnderTest.publish(
                AnnouncementBuilder
                        .newAnnouncement()
                        .build()
        )

        then:
        thrown(NoPublisherSpecifiedException.class)
    }

    def "should dispatch publish request to all children otherwise"() {
        given:
        def objectUnderTest = new PublisherCoordinator();
        def firstPublisherMock = Mock(Publisher.class)
        def secondPublisherMock = Mock(Publisher.class)

        def announcement = AnnouncementBuilder
                .newAnnouncement()
                .build()

        when:
        objectUnderTest
                .register(firstPublisherMock)
                .register(secondPublisherMock)

        objectUnderTest.publish announcement

        then:
        1 * firstPublisherMock.publish(announcement)
        1 * secondPublisherMock.publish(announcement)

    }
}
