package knbit.events.bc.announcement.config;

import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.coordinator.PublisherCoordinator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

/**
 * Created by novy on 03.04.15.
 */

@Configuration
class CoordinatorConfig {

    @Bean(name = Publishers.COORDINATOR)
    @Autowired
    public Publisher coordinator(Collection<Publisher> publishers) {
        final PublisherCoordinator coordinator = new PublisherCoordinator();

        publishers.forEach(coordinator::register);

        return coordinator;
    }

}
