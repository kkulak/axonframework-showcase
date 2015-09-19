package knbit.events.bc.announcement.googlegroup.configuration;

import knbit.events.bc.PublisherConfigurationRepository;

import java.util.Optional;

/**
 * Created by novy on 19.09.15.
 */
public interface GoogleGroupConfigurationRepository extends PublisherConfigurationRepository<GoogleGroupConfiguration, Long> {

    Optional<GoogleGroupConfiguration> findOne(Long id);

}
