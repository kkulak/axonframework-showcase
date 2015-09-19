package knbit.events.bc.announcement.web

import knbit.events.bc.announcement.TestPublisherConfiguration
import knbit.events.bc.announcement.configuration.AllConfigurationQuery
import knbit.events.bc.announcement.publishers.PublisherFactory
import knbit.events.bc.announcement.publishers.PublisherVendor
import spock.lang.Specification

/**
 * Created by novy on 19.09.15.
 */
class AnnouncementControllerTest extends Specification {

    def PublisherFactory publisherFactoryMock
    def AllConfigurationQuery configurationQueryMock

    def AnnouncementController objectUnderTest

    void setup() {
        publisherFactoryMock = Mock(PublisherFactory)
        configurationQueryMock = Mock(AllConfigurationQuery)

        objectUnderTest = new AnnouncementController(
                publisherFactoryMock,
                configurationQueryMock,
                Mock(AggregatingPublisher)
        )
    }

    def "if no publisher specified, should use default ones"() {
        given:
        def testPublisherConfiguration = new TestPublisherConfiguration(666L, "name", true, PublisherVendor.FACEBOOK)
        configurationQueryMock.defaults() >> [testPublisherConfiguration]

        when:
        def emptyImageUrl = ""
        def announcementDTOWithoutPublishers = new AnnouncementDTO([], "title", "content", emptyImageUrl)
        objectUnderTest.postAnnouncement(announcementDTOWithoutPublishers)

        then:
        1 * publisherFactoryMock.byIdsAndVendors(
                [new ConfigurationIdAndVendor(666L, PublisherVendor.FACEBOOK)]
        )
    }

    def "otherwise it should used provided publishers"() {
        given:
        def providedIdAndVendor = new ConfigurationIdAndVendor(333L, PublisherVendor.GOOGLE_GROUP)

        when:
        def emptyImageUrl = ""
        def announcementDTOWithoutPublishers = new AnnouncementDTO([providedIdAndVendor], "title", "content", emptyImageUrl)
        objectUnderTest.postAnnouncement(announcementDTOWithoutPublishers)

        then:
        1 * publisherFactoryMock.byIdsAndVendors([providedIdAndVendor])
    }
}
