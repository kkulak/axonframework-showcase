package knbit.events.bc.eventready.infrastructure;

import knbit.events.bc.eventready.domain.aggregates.ReadyEvent;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by novy on 24.10.15.
 */

@Configuration("readyEventRepositories")
public class Repositories {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventBus eventBus;

    @Bean
    public Repository<ReadyEvent> readyEventRepository() {
        EventSourcingRepository<ReadyEvent> repository = new EventSourcingRepository<>(
                ReadyEvent.class, eventStore);

        repository.setEventBus(eventBus);

        return repository;
    }
}
