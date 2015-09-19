package knbit.events.bc.announcement.iietboard.configuration;

import knbit.events.bc.PublisherConfigurationRepository;

import java.util.Optional;

/**
 * Created by novy on 19.09.15.
 */
public interface IIETBoardConfigurationRepository extends PublisherConfigurationRepository<IIETBoardConfiguration, Long> {

    Optional<IIETBoardConfiguration> findOne(Long id);
}
