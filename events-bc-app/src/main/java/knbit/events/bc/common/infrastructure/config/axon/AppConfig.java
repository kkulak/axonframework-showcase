package knbit.events.bc.common.infrastructure.config.axon;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.java.SimpleEventSchedulerFactoryBean;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.EventFileResolver;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.saga.SagaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created by novy on 04.05.15.
 */

@Configuration
public class AppConfig {
    @Autowired
    private SagaManager sagaManager;
    private static final String EVENTSTORE_FILEPATH = "data/eventstore";

    @Bean
    public EventBus eventBus() {
        final SimpleEventBus eventBus = new SimpleEventBus();
        eventBus.subscribe(sagaManager);
        return eventBus;
    }

    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
        AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();
        processor.setEventBus(
                eventBus()
        );
        return processor;
    }

    @Bean
    public CommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
        processor.setCommandBus(
                commandBus()
        );
        return processor;
    }

    @Bean
    public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean() {
        CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<>();
        factory.setCommandBus(
                commandBus()
        );
        return factory;
    }

    @Bean
    public EventScheduler eventScheduler() throws Exception {
        return new SimpleEventSchedulerFactoryBean().getObject();
    }

    @Bean
    public EventStore eventStore() {
        final File eventStoreFile = new File(EVENTSTORE_FILEPATH);
        final EventFileResolver eventFileResolver = new SimpleEventFileResolver(eventStoreFile);
        return new FileSystemEventStore(
                eventFileResolver
        );
    }

}
