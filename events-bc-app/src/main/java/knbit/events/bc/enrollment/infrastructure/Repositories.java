package knbit.events.bc.enrollment.infrastructure;

import knbit.events.bc.enrollment.domain.aggregates.EventUnderEnrollment;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by novy on 02.10.15.
 */

@Configuration("eventUnderEnrollmentRepositories")
public class Repositories {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventBus eventBus;

    @Bean
    public Repository<EventUnderEnrollment> eventUnderEnrollmentRepository() {
        EventSourcingRepository<EventUnderEnrollment> repository = new EventSourcingRepository<>(
                EventUnderEnrollment.class, eventStore);

        repository.setEventBus(eventBus);

        return repository;
    }
}
