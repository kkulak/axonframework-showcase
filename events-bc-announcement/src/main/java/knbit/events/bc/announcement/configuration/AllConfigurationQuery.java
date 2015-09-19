package knbit.events.bc.announcement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by novy on 07.04.15.
 */

@Repository
public class AllConfigurationQuery {

    private final Collection<PublisherConfigurationRepository<? extends PublisherConfiguration, Long>> repositories;

    @Autowired
    public AllConfigurationQuery(
            Collection<PublisherConfigurationRepository<? extends PublisherConfiguration, Long>> repositories) {
        this.repositories = repositories;
    }

    public Collection<PublisherConfiguration> allConfigurations() {
        return allConfigurationsStream()
                .collect(Collectors.toList());
    }

    public Collection<PublisherConfiguration> defaults() {
        return allConfigurationsStream()
                .filter(PublisherConfiguration::isDefaultPublisher)
                .collect(Collectors.toList());
    }

    private Stream<PublisherConfiguration> allConfigurationsStream() {
        return repositories
                .stream()
                .flatMap(repository -> repository.findAll().stream());
    }
}
