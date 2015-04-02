package knbit.events.bc.announcment.composite

import knbit.events.bc.announcment.AnnouncementBuilder
import knbit.events.bc.announcement.Publisher
import knbit.events.bc.announcement.composite.CompositePublisher
import knbit.events.bc.announcement.composite.NoPublisherSpecifiedException
import spock.lang.Specification

/**
 * Created by novy on 02.04.15.
 */
class CompositePublisherTest extends Specification {

    def "should thrown an exception when no publisher specified"() {
        given:
        def objectUnderTest = new CompositePublisher();

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
        def objectUnderTest = new CompositePublisher();
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
