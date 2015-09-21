package knbit.events.bc.announcement.configuration

import knbit.events.bc.announcement.TestPublisherConfiguration
import knbit.events.bc.announcement.publishers.PublisherVendor
import spock.lang.Specification

/**
 * Created by novy on 19.09.15.
 */
class AllConfigurationQueryTest extends Specification {

    def "asked for all configurations, should merge result of repositories"() {
        when:
        def configurationFromFirstRepository =
                new TestPublisherConfiguration(1, "first", true, PublisherVendor.FACEBOOK)
        def firstConfigurationRepository = Mock(PublisherConfigurationRepository)
        firstConfigurationRepository.findAll() >> [configurationFromFirstRepository]

        def configurationFromSecondRepository =
                new TestPublisherConfiguration(1, "second", false, PublisherVendor.TWITTER)
        def secondConfigurationRepository = Mock(PublisherConfigurationRepository)
        secondConfigurationRepository.findAll() >> [configurationFromSecondRepository]

        def AllConfigurationQuery objectUnderTest = new AllConfigurationQuery([firstConfigurationRepository, secondConfigurationRepository])

        then:
        objectUnderTest.allConfigurations() == [configurationFromFirstRepository, configurationFromSecondRepository]
    }

    def "asked for defaults, should select only these with truthy isDefault"() {
        when:
        def defaultConfiguration =
                new TestPublisherConfiguration(1, "first", true, PublisherVendor.FACEBOOK)
        def firstConfigurationRepository = Mock(PublisherConfigurationRepository)
        firstConfigurationRepository.findAll() >> [defaultConfiguration]

        def nonDefaultConfiguration =
                new TestPublisherConfiguration(1, "second", false, PublisherVendor.TWITTER)
        def secondConfigurationRepository = Mock(PublisherConfigurationRepository)
        secondConfigurationRepository.findAll() >> [nonDefaultConfiguration]

        def AllConfigurationQuery objectUnderTest = new AllConfigurationQuery([firstConfigurationRepository, secondConfigurationRepository])

        then:
        objectUnderTest.defaults() == [defaultConfiguration]
    }
}
