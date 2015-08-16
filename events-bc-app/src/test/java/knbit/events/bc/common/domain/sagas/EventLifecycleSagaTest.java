package knbit.events.bc.common.domain.sagas;

import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventCreated;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitedToInterestAwareEvent;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitedToUnderChoosingTermEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.CreateEventUnderChoosingTermCommand;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.domain.valueobjects.commands.CreateInterestAwareEventCommand;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventTransitedToUnderChoosingTermEvent;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;

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
    public void shouldStartSagaOnBacklogEventCreation() throws Exception {
        fixture
                .whenAggregate(eventId)
                .publishes(
                        BacklogEventCreated.of(eventId, eventDetails)
                )
                .expectActiveSagas(1);
    }

    @Test
    public void shouldDispatchCreateInterestAwareEventOnBacklogEventTransitedToInterestAwareEvent() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventCreated.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        BacklogEventTransitedToInterestAwareEvent.of(eventId, eventDetails)
                )
                .expectDispatchedCommandsEqualTo(
                        CreateInterestAwareEventCommand.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldDispatchCreateEventUnderChoosingTermCommandOnBacklogEventTransitedToChoosingTermEvent() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventCreated.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        BacklogEventTransitedToUnderChoosingTermEvent.of(eventId, eventDetails)
                )
                .expectDispatchedCommandsEqualTo(
                        CreateEventUnderChoosingTermCommand.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldDispatchCreateEventUnderChoosingTermCommandOnInterestAwareEventTransitedToChoosingTermEvent() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventCreated.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        InterestAwareEventTransitedToUnderChoosingTermEvent.of(eventId, eventDetails)
                )
                .expectDispatchedCommandsEqualTo(
                        CreateEventUnderChoosingTermCommand.of(eventId, eventDetails)
                );
    }
}
