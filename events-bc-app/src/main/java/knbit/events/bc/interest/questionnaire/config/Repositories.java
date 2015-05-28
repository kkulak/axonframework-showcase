package knbit.events.bc.interest.questionnaire.config;

import knbit.events.bc.interest.questionnaire.domain.aggregates.Questionnaire;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by novy on 26.05.15.
 */
@Configuration(value = "questionnaireRepositories")
public class Repositories {

    @Bean
    public Repository<Questionnaire> questionnaireRepository(EventStore eventStore, EventBus eventBus) {
        EventSourcingRepository<Questionnaire> repository = new EventSourcingRepository<>(
                Questionnaire.class, eventStore);

        repository.setEventBus(eventBus);

        return repository;
    }
}
