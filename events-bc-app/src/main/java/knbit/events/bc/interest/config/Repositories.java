package knbit.events.bc.interest.config;

import knbit.events.bc.interest.domain.aggregates.InterestAwareEvent;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by novy on 28.05.15.
 */
@Configuration(value = "interestAwareEventRepositories")
public class Repositories {

    @Bean
    public Repository<InterestAwareEvent> interestAwareEventRepository(EventStore eventStore, EventBus eventBus) {
        EventSourcingRepository<InterestAwareEvent> repository = new EventSourcingRepository<>(
                InterestAwareEvent.class, eventStore);

        repository.setEventBus(eventBus);

        return repository;
    }
}
