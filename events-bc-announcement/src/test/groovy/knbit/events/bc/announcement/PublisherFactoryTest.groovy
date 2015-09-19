package knbit.events.bc.announcement

import knbit.events.bc.announcement.builders.FacebookConfigurationBuilder
import knbit.events.bc.announcement.builders.GoogleGroupConfigurationBuilder
import knbit.events.bc.announcement.builders.IIETBoardConfigurationBuilder
import knbit.events.bc.announcement.builders.TwitterConfigurationBuilder
import knbit.events.bc.announcement.facebook.configuration.FacebookConfigurationRepository
import knbit.events.bc.announcement.facebook.publisher.FacebookPublisher
import knbit.events.bc.announcement.facebook.publisher.FacebookPublisherFactory
import knbit.events.bc.announcement.googlegroup.configuration.GoogleGroupConfigurationRepository
import knbit.events.bc.announcement.googlegroup.publisher.GoogleGroupPublisher
import knbit.events.bc.announcement.googlegroup.publisher.GoogleGroupPublisherFactory
import knbit.events.bc.announcement.iietboard.configuration.IIETBoardConfigurationRepository
import knbit.events.bc.announcement.iietboard.publisher.IIETBoardPublisher
import knbit.events.bc.announcement.iietboard.publisher.IIETBoardPublisherFactory
import knbit.events.bc.announcement.twitter.configuration.TwitterConfigurationRepository
import knbit.events.bc.announcement.twitter.publisher.TwitterPublisher
import knbit.events.bc.announcement.twitter.publisher.TwitterPublisherFactory
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
