package knbit.events.bc.common.infrastructure.config.axon;

import knbit.events.bc.eventproposal.domain.sagas.EventCreationalSaga;
import org.axonframework.saga.SagaFactory;
import org.axonframework.saga.SagaRepository;
import org.axonframework.saga.annotation.AnnotatedSagaManager;
import org.axonframework.saga.repository.inmemory.InMemorySagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SagaConfig {

    @Autowired
    private SagaFactory sagaFactory;

    @Bean
    public SagaRepository sagaRepository() {
        return new InMemorySagaRepository();
    }

    @Bean
    public AnnotatedSagaManager sagaManager() {
        return new AnnotatedSagaManager(
                sagaRepository(), sagaFactory, EventCreationalSaga.class
        );
    }

}
