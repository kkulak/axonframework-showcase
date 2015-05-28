package knbit.events.bc.common.infrastructure.config.axon;

import knbit.events.bc.eventproposal.domain.sagas.EventCreationalSaga;
import knbit.events.bc.interest.common.domain.sagas.InterestSaga;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.quartz.QuartzEventScheduler;
import org.axonframework.saga.ResourceInjector;
import org.axonframework.saga.SagaFactory;
import org.axonframework.saga.SagaRepository;
import org.axonframework.saga.annotation.AnnotatedSagaManager;
import org.axonframework.saga.repository.inmemory.InMemorySagaRepository;
import org.axonframework.saga.spring.SpringResourceInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SagaConfig {

    @Bean
    public SagaRepository sagaRepository() {
        return new InMemorySagaRepository();
    }

    @Bean
    public AnnotatedSagaManager sagaManager(SagaRepository sagaRepository, SagaFactory sagaFactory, EventBus eventBus) {
        final AnnotatedSagaManager annotatedSagaManager = new AnnotatedSagaManager(
                sagaRepository, sagaFactory,
                EventCreationalSaga.class, InterestSaga.class
        );
        eventBus.subscribe(annotatedSagaManager);
        return annotatedSagaManager;
    }

    @Bean
    public EventScheduler eventScheduler(EventBus eventBus, SchedulerFactoryBean schedulerFactoryBean) {
        final QuartzEventScheduler quartzEventScheduler = new QuartzEventScheduler();
        quartzEventScheduler.setEventBus(eventBus);
        quartzEventScheduler.setScheduler(schedulerFactoryBean.getScheduler());

        return quartzEventScheduler;
    }

    @Bean
    public ResourceInjector resourceInjector() {
        return new SpringResourceInjector();
    }

    @Bean
    public SchedulerFactoryBean eventSchedulerFactoryBean() {
        return new SchedulerFactoryBean();
    }

}
