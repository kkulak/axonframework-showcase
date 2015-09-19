package knbit.events.bc.announcement

import knbit.events.bc.PublisherConfigurationRepository
import knbit.events.bc.announcement.AllConfigurationQuery
import knbit.events.bc.announcement.PublisherConfiguration
import knbit.events.bc.announcement.PublisherVendor
import spock.lang.Specification

/**
 * Created by novy on 19.09.15.
 */
class AllConfigurationQueryTest extends Specification {

    def "asked for all configurations, should merge result of repositories"() {
        when:
        def configurationFromFirstRepository =
                new PublisherConfigurationTestImplementation(1, "first", true, PublisherVendor.FACEBOOK)
        def firstConfigurationRepository = Mock(PublisherConfigurationRepository)
        firstConfigurationRepository.findAll() >> [configurationFromFirstRepository]

        def configurationFromSecondRepository =
                new PublisherConfigurationTestImplementation(1, "second", false, PublisherVendor.TWITTER)
        def secondConfigurationRepository = Mock(PublisherConfigurationRepository)
        secondConfigurationRepository.findAll() >> [configurationFromSecondRepository]

        def AllConfigurationQuery objectUnderTest = new AllConfigurationQuery([firstConfigurationRepository, secondConfigurationRepository])

        then:
        objectUnderTest.allConfigurations() == [configurationFromFirstRepository, configurationFromSecondRepository]
    }

    def "asked for defaults, should select only these with truthy isDefault"() {
        when:
        def defaultConfiguration =
                new PublisherConfigurationTestImplementation(1, "first", true, PublisherVendor.FACEBOOK)
        def firstConfigurationRepository = Mock(PublisherConfigurationRepository)
        firstConfigurationRepository.findAll() >> [defaultConfiguration]

        def nonDefaultConfiguration =
                new PublisherConfigurationTestImplementation(1, "second", false, PublisherVendor.TWITTER)
        def secondConfigurationRepository = Mock(PublisherConfigurationRepository)
        secondConfigurationRepository.findAll() >> [nonDefaultConfiguration]

        def AllConfigurationQuery objectUnderTest = new AllConfigurationQuery([firstConfigurationRepository, secondConfigurationRepository])

        then:
        objectUnderTest.defaults() == [defaultConfiguration]
    }

    private static class PublisherConfigurationTestImplementation implements PublisherConfiguration {
        private Long id;
        private String name;
        private boolean isDefault;
        private PublisherVendor vendor;

        PublisherConfigurationTestImplementation(Long id, String name, boolean isDefault, PublisherVendor vendor) {
            this.id = id
            this.name = name
            this.isDefault = isDefault
            this.vendor = vendor
        }

        @Override
        Long getId() {
            return id
        }

        @Override
        String getName() {
            return name
        }

        @Override
        boolean isDefault() {
            return isDefault
        }

        @Override
        PublisherVendor getVendor() {
            return vendor
        }
    }
}
