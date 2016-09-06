package knbit.events.bc.announcement.publishers

import knbit.events.bc.announcement.builders.FacebookConfigurationBuilder
import knbit.events.bc.announcement.builders.GoogleGroupConfigurationBuilder
import knbit.events.bc.announcement.builders.IIETBoardConfigurationBuilder
import knbit.events.bc.announcement.builders.TwitterConfigurationBuilder
import knbit.events.bc.announcement.configuration.facebook.FacebookConfigurationRepository
import knbit.events.bc.announcement.configuration.googlegroup.GoogleGroupConfigurationRepository
import knbit.events.bc.announcement.configuration.iietboard.IIETBoardConfigurationRepository
import knbit.events.bc.announcement.configuration.twitter.TwitterConfigurationRepository
import knbit.events.bc.announcement.publishers.facebook.FacebookPublisher
import knbit.events.bc.announcement.publishers.facebook.FacebookPublisherFactory
import knbit.events.bc.announcement.publishers.googlegroup.GoogleGroupPublisher
import knbit.events.bc.announcement.publishers.googlegroup.GoogleGroupPublisherFactory
import knbit.events.bc.announcement.publishers.iietboard.IIETBoardPublisher
import knbit.events.bc.announcement.publishers.iietboard.IIETBoardPublisherFactory
import knbit.events.bc.announcement.publishers.twitter.TwitterPublisher
import knbit.events.bc.announcement.publishers.twitter.TwitterPublisherFactory
import knbit.events.bc.announcement.web.ConfigurationIdAndVendor
import spock.lang.Specification

/**
 * Created by novy on 12.04.15.
 */
class PublisherFactoryTest extends Specification {

    def FacebookConfigurationRepository facebookConfigurationRepository
    def TwitterConfigurationRepository twitterConfigurationRepository
    def GoogleGroupConfigurationRepository googleGroupConfigurationRepository
    def IIETBoardConfigurationRepository iietBoardConfigurationRepository

    def PublisherFactory objectUnderTest

    void setup() {
        facebookConfigurationRepository = Mock(FacebookConfigurationRepository)
        twitterConfigurationRepository = Mock(TwitterConfigurationRepository)
        googleGroupConfigurationRepository = Mock(GoogleGroupConfigurationRepository)
        iietBoardConfigurationRepository = Mock(IIETBoardConfigurationRepository)

        objectUnderTest = new PublisherFactory(
                new FacebookPublisherFactory(facebookConfigurationRepository),
                new TwitterPublisherFactory(twitterConfigurationRepository),
                new GoogleGroupPublisherFactory(googleGroupConfigurationRepository),
                new IIETBoardPublisherFactory(iietBoardConfigurationRepository)
        )
    }

    def "should return facebook publisher when needed"() {

        given:
        facebookConfigurationRepository.findOne(666L) >> Optional.of(
                FacebookConfigurationBuilder
                        .newFacebookConfiguration()
                        .id(666L)
                        .build()
        )

        when:
        def Publisher publisher = objectUnderTest.byIdAndVendor(
                new ConfigurationIdAndVendor(666L, PublisherVendor.FACEBOOK)
        )

        then:
        publisher instanceof FacebookPublisher

    }

    def "should return twitter publisher when needed"() {

        given:
        twitterConfigurationRepository.findOne(666L) >> Optional.of(
                TwitterConfigurationBuilder
                        .newTwitterConfiguration()
                        .id(666L)
                        .build()
        )

        when:
        def Publisher publisher = objectUnderTest.byIdAndVendor(
                new ConfigurationIdAndVendor(666L, PublisherVendor.TWITTER)
        )

        then:
        publisher instanceof TwitterPublisher

    }

    def "should return googlegroup publisher when needed"() {

        given:
        googleGroupConfigurationRepository.findOne(666L) >> Optional.of(
                GoogleGroupConfigurationBuilder
                        .newGoogleGroupConfiguration()
                        .id(666L)
                        .build()
        )

        when:
        def Publisher publisher = objectUnderTest.byIdAndVendor(
                new ConfigurationIdAndVendor(666L, PublisherVendor.GOOGLE_GROUP)
        )

        then:
        publisher instanceof GoogleGroupPublisher

    }

    def "should return iietboard publisher when needed"() {

        given:
        iietBoardConfigurationRepository.findOne(666L) >> Optional.of(
                IIETBoardConfigurationBuilder
                        .newIIETBoardProperties()
                        .id(666L)
                        .build()
        )

        when:
        def Publisher publisher = objectUnderTest.byIdAndVendor(
                new ConfigurationIdAndVendor(666L, PublisherVendor.IIET_BOARD)
        )

        then:
        publisher instanceof IIETBoardPublisher

    }

    def "should throw an exception given null"() {
        when:
        objectUnderTest.byIdAndVendor(null)

        then:
        thrown(NullPointerException.class)
    }
}
