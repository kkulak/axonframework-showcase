package knbit.events.bc.backlogevent.infrastructure.config;

import knbit.events.bc.backlogevent.domain.aggregates.BacklogEvent;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "backlogEventRepositories")
public class Repositories {
    @Autowired
    private EventStore store;
    @Autowired
    private EventBus bus;

    @Bean
    public Repository<BacklogEvent> eventRepository() {
        EventSourcingRepository<BacklogEvent> repository = new EventSourcingRepository<>(BacklogEvent.class, store);
        repository.setEventBus(bus);
        return repository;
    }

}
