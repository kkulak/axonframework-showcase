package knbit.events.bc.eventproposal.infrastructure.config;

import knbit.events.bc.eventproposal.domain.aggregates.EventProposal;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by novy on 05.05.15.
 */

@Configuration(value = "eventProposalRepositories")
public class Repositories {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventBus eventBus;

    @Bean
    public Repository<EventProposal> eventProposalRepository() {
        EventSourcingRepository<EventProposal> repository = new EventSourcingRepository<>(
                EventProposal.class, eventStore);

        repository.setEventBus(eventBus);

        return repository;
    }
}
