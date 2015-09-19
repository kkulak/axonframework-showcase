package knbit.events.bc.announcement.configuration.googlegroup;

import knbit.events.bc.announcement.configuration.PublisherConfigurationRepository;

import java.util.Optional;

/**
 * Created by novy on 19.09.15.
 */
public interface GoogleGroupConfigurationRepository extends PublisherConfigurationRepository<GoogleGroupConfiguration, Long> {

    Optional<GoogleGroupConfiguration> findOne(Long id);

}
