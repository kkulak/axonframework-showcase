package knbit.events.bc.common.infrastructure.config.axon;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import knbit.events.bc.common.Profiles;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.EventFileResolver;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.joda.time.DateTimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Created by novy on 04.05.15.
 */

@Slf4j
@Configuration
public class AppConfig {

    private static final String EVENTSTORE_FILEPATH = "data/eventstore";

    @PostConstruct
    private void setDefaultJodaTimeZone() {
        // to prevent joda from setting local timezone while replaying events
        DateTimeZone.setDefault(DateTimeZone.UTC);
    }

    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor(EventBus eventBus) {
        AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();
        processor.setEventBus(
                eventBus
        );
        return processor;
    }

    @Bean
    public CommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor(CommandBus commandBus) {
        AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
        processor.setCommandBus(
                commandBus
        );
        return processor;
    }

    @Bean
    public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean(CommandBus commandBus) {
        CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<>();
        factory.setCommandBus(
                commandBus
        );
        return factory;
    }


    @Bean
    @Profile("!" + Profiles.PROD)
    public EventStore fileSystemEventStore() {
        final File eventStoreFile = new File(EVENTSTORE_FILEPATH);
        final EventFileResolver eventFileResolver = new SimpleEventFileResolver(eventStoreFile);
        return new FileSystemEventStore(
                eventFileResolver
        );
    }

    @Bean
    @Profile(Profiles.PROD)
    public EventStore mongoEventStore(Mongo mongo) {
        log.info("creating mongo event store");
        final DefaultMongoTemplate mongoTemplate = new DefaultMongoTemplate(mongo);
        return new MongoEventStore(mongoTemplate);
    }

    @Bean
    @Profile(Profiles.PROD)
    public Mongo mongoClient() throws Exception {
        return new MongoClient("eventsbcmongo");
    }
}
