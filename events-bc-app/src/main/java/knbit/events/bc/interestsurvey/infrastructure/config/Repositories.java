package knbit.events.bc.interestsurvey.infrastructure.config;

import knbit.events.bc.interestsurvey.domain.aggreagates.Survey;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by novy on 28.05.15.
 */
@Configuration(value = "surveyRepositories")
public class Repositories {

    @Bean
    public Repository<Survey> questionnaireRepository(EventStore eventStore, EventBus eventBus) {
        EventSourcingRepository<Survey> repository = new EventSourcingRepository<>(
                Survey.class, eventStore);

        repository.setEventBus(eventBus);

        return repository;
    }
}
