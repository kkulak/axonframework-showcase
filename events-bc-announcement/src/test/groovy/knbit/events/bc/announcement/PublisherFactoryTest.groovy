package knbit.events.bc.announcement

import knbit.events.bc.announcement.AllConfigurationQuery
import knbit.events.bc.announcement.Publisher
import knbit.events.bc.announcement.PublisherFactory
import knbit.events.bc.announcement.PublisherVendor
import knbit.events.bc.announcement.builders.FacebookPropertiesBuilder
import knbit.events.bc.announcement.builders.GoogleGroupPropertiesBuilder
import knbit.events.bc.announcement.builders.IIETBoardPropertiesBuilder
import knbit.events.bc.announcement.builders.TwitterPropertiesBuilder
import knbit.events.bc.announcement.facebook.publisher.FacebookPublisher
import knbit.events.bc.announcement.googlegroup.publisher.GoogleGroupPublisher
import knbit.events.bc.announcement.iietboard.publisher.IIETBoardPublisher
import knbit.events.bc.announcement.twitter.publisher.TwitterPublisher
import spock.lang.Specification

/**
 * Created by novy on 12.04.15.
 */
class PublisherFactoryTest extends Specification {

    def "should return facebook publisher when needed"() {

        given:
        def configurationRepositoryMock = Mock(AllConfigurationQuery.class)
        configurationRepositoryMock.facebookConfiguration() >> FacebookPropertiesBuilder
                .newFacebookProperties()
                .build()
        def objectUnderTest = new PublisherFactory(configurationRepositoryMock)

        when:
        def Publisher publisher = objectUnderTest.byVendor(PublisherVendor.FACEBOOK)

        then:
        publisher instanceof FacebookPublisher

    }

    def "should return twitter publisher when needed"() {

        given:
        def configurationRepositoryMock = Mock(AllConfigurationQuery.class)
        configurationRepositoryMock.twitterConfiguration() >> TwitterPropertiesBuilder
                .newTwitterProperties()
                .build()
        def objectUnderTest = new PublisherFactory(configurationRepositoryMock)

        when:
        def Publisher publisher = objectUnderTest.byVendor(PublisherVendor.TWITTER)

        then:
        publisher instanceof TwitterPublisher

    }

    def "should return googlegroup publisher when needed"() {

        given:
        def configurationRepositoryMock = Mock(AllConfigurationQuery.class)
        configurationRepositoryMock.googleGroupConfiguration() >> GoogleGroupPropertiesBuilder
                .newGoogleGroupProperties()
                .build()
        def objectUnderTest = new PublisherFactory(configurationRepositoryMock)

        when:
        def Publisher publisher = objectUnderTest.byVendor(PublisherVendor.GOOGLE_GROUP)

        then:
        publisher instanceof GoogleGroupPublisher

    }

    def "should return iietboard publisher when needed"() {

        given:
        def configurationRepositoryMock = Mock(AllConfigurationQuery.class)
        configurationRepositoryMock.iietBoardConfiguration() >> IIETBoardPropertiesBuilder
                .newIIETBoardProperties()
                .build()
        def objectUnderTest = new PublisherFactory(configurationRepositoryMock)

        when:
        def Publisher publisher = objectUnderTest.byVendor(PublisherVendor.IIET_BOARD)

        then:
        publisher instanceof IIETBoardPublisher

    }

    def "should throw an exception given null"() {

        given:
        def objectUnderTest = new PublisherFactory(
                Mock(AllConfigurationQuery.class)
        )

        when:
        objectUnderTest.byVendor(null)

        then:
        thrown(NullPointerException.class)

    }
}
