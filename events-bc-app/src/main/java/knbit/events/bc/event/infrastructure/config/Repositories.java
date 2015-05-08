package knbit.events.bc.event.infrastructure.config;

import knbit.events.bc.event.domain.aggregates.AbstractEvent;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Repositories {
    @Autowired private EventStore store;
    @Autowired private EventBus bus;

    @Bean
    public Repository<AbstractEvent> eventRepository() {
        EventSourcingRepository<AbstractEvent> repository = new EventSourcingRepository<>(
                new AxonEventFactory(), store);
        repository.setEventBus(bus);
        return repository;
    }

}
