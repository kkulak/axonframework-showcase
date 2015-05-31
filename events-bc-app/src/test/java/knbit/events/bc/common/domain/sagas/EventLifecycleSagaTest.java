package knbit.events.bc.common.domain.sagas;

import knbit.events.bc.backlogevent.domain.builders.BacklogEventCreatedBuilder;
import knbit.events.bc.backlogevent.domain.builders.BacklogEventDeactivatedBuilder;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.CreateBacklogEventCommand;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.domain.sagas.InterestSaga;
import knbit.events.bc.interest.domain.valueobjects.commands.CreateInterestAwareEventCommand;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import static knbit.events.bc.common.domain.enums.EventType.*;

public class EventLifecycleSagaTest {
    private AnnotatedSagaTestFixture fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = new AnnotatedSagaTestFixture(EventLifecycleSaga.class);
        eventId = EventId.of("id");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
    }

    @Test
    public void shouldStartSagaOnBacklogEventDeactivatedEvent() throws Exception {
        fixture
                .whenAggregate(
                        eventId
                )
                .publishes(
                        BacklogEventDeactivatedBuilder
                            .instance()
                            .build()
                )
                .expectActiveSagas(1);
    }

    @Test
    public void shouldDispatchCreateInterestAwareEventOnBacklogEventDeactivatedEvent() throws Exception {
        fixture
                .whenAggregate(
                        eventId
                )
                .publishes(
                        BacklogEventDeactivatedBuilder
                                .instance()
                                .description(Description.of("desc"))
                                .type(WORKSHOP)
                                .build()
                )
                .expectDispatchedCommandsEqualTo(
                        CreateInterestAwareEventCommand.of(eventId, eventDetails)
                );
    }

}
