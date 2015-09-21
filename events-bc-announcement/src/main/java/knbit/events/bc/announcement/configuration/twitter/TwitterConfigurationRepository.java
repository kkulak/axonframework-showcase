package knbit.events.bc.announcement.configuration.twitter;

import knbit.events.bc.announcement.configuration.PublisherConfigurationRepository;

import java.util.Optional;

/**
 * Created by novy on 19.09.15.
 */
public interface TwitterConfigurationRepository extends PublisherConfigurationRepository<TwitterConfiguration, Long> {

    Optional<TwitterConfiguration> findOne(Long id);
}
