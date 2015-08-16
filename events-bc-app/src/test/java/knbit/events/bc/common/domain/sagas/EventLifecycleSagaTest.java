package knbit.events.bc.common.domain.sagas;

import knbit.events.bc.backlogevent.domain.builders.BacklogEventCreatedBuilder;
import knbit.events.bc.backlogevent.domain.builders.BacklogEventDeactivatedBuilder;
import knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventState;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventCreated;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitedToChoosingTermEvent;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitedToInterestAwareEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.CreateEventUnderChoosingTermCommand;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.domain.valueobjects.commands.CreateInterestAwareEventCommand;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventTransitedToChoosingTermEvent;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import static knbit.events.bc.common.domain.enums.EventType.WORKSHOP;

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
                        BacklogEventTransitedToInterestAwareEvent.of(eventId, eventDetails, BacklogEventState.INACTIVE)
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
                        BacklogEventTransitedToChoosingTermEvent.of(eventId, eventDetails)
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
                        InterestAwareEventTransitedToChoosingTermEvent.of(eventId, eventDetails)
                )
                .expectDispatchedCommandsEqualTo(
                        CreateEventUnderChoosingTermCommand.of(eventId, eventDetails)
                );
    }
}
