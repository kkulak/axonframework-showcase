package knbit.events.bc.announcement.config

import knbit.events.bc.announcement.Publisher
import knbit.events.bc.announcement.facebook.FacebookPublisher
import knbit.events.bc.announcement.googlegroup.GoogleGroupPublisher
import knbit.events.bc.announcement.iietboard.IIETBoardPublisher
import knbit.events.bc.announcement.twitter.TwitterPublisher
import spock.lang.Specification

/**
 * Created by novy on 12.04.15.
 */
class PublisherFactoryTest extends Specification {

    def "should return facebook publisher when needed"() {

        given:
        def configurationRepositoryMock = Mock(ConfigurationRepository.class)
        configurationRepositoryMock.facebookProperties() >> FacebookPropertiesBuilder
                .newFacebookProperties()
                .build()
        def objectUnderTest = new PublisherFactory(configurationRepositoryMock)

        when:
        def Publisher publisher = objectUnderTest.byVendor(Publishers.FACEBOOK)

        then:
        publisher instanceof FacebookPublisher

    }

    def "should return twitter publisher when needed"() {

        given:
        def configurationRepositoryMock = Mock(ConfigurationRepository.class)
        configurationRepositoryMock.twitterProperties() >> TwitterPropertiesBuilder
                .newTwitterProperties()
                .build()
        def objectUnderTest = new PublisherFactory(configurationRepositoryMock)

        when:
        def Publisher publisher = objectUnderTest.byVendor(Publishers.TWITTER)

        then:
        publisher instanceof TwitterPublisher

    }

    def "should return googlegroup publisher when needed"() {

        given:
        def configurationRepositoryMock = Mock(ConfigurationRepository.class)
        configurationRepositoryMock.googleGroupProperties() >> GoogleGroupPropertiesBuilder
                .newGoogleGroupProperties()
                .build()
        def objectUnderTest = new PublisherFactory(configurationRepositoryMock)

        when:
        def Publisher publisher = objectUnderTest.byVendor(Publishers.GOOGLE_GROUP)

        then:
        publisher instanceof GoogleGroupPublisher

    }

    def "should return iietboard publisher when needed"() {

        given:
        def configurationRepositoryMock = Mock(ConfigurationRepository.class)
        configurationRepositoryMock.iietBoardProperties() >> IIETBoardPropertiesBuilder
                .newIIETBoardProperties()
                .build()
        def objectUnderTest = new PublisherFactory(configurationRepositoryMock)

        when:
        def Publisher publisher = objectUnderTest.byVendor(Publishers.IIET_BOARD)

        then:
        publisher instanceof IIETBoardPublisher

    }

    def "should throw an exception given null"() {

        given:
        def objectUnderTest = new PublisherFactory(
                Mock(ConfigurationRepository.class)
        )

        when:
        objectUnderTest.byVendor(null)

        then:
        thrown(NullPointerException.class)

    }
}