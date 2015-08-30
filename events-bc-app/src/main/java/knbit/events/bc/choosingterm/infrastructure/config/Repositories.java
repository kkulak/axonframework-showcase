package knbit.events.bc.choosingterm.infrastructure.config;

import knbit.events.bc.choosingterm.domain.aggregates.UnderChoosingTermEvent;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by novy on 16.08.15.
 */
@Configuration(value = "underChoosingTermEventRepositories")
public class Repositories {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventBus eventBus;

    @Bean
    public Repository<UnderChoosingTermEvent> underChoosingTermEventRepository() {
        EventSourcingRepository<UnderChoosingTermEvent> repository = new EventSourcingRepository<>(
                UnderChoosingTermEvent.class, eventStore);

        repository.setEventBus(eventBus);

        return repository;
    }
}
