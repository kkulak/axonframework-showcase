package knbit.events.bc.common.domain.sagas;

import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitionEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.domain.valueobjects.commands.InterestAwareEventCommands;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
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
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .expectActiveSagas(1);
    }

    @Test
    public void shouldDispatchCreateInterestAwareEventOnBacklogEventTransitedToInterestAwareEvent() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        BacklogEventTransitionEvents.TransitedToInterestAware.of(eventId, eventDetails)
                )
                .expectDispatchedCommandsEqualTo(
                        InterestAwareEventCommands.Create.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldDispatchCreateEventUnderChoosingTermCommandOnBacklogEventTransitedToChoosingTermEvent() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        BacklogEventTransitionEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)
                )
                .expectDispatchedCommandsEqualTo(
                        UnderChoosingTermEventCommands.Create.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldDispatchCreateEventUnderChoosingTermCommandOnInterestAwareEventTransitedToChoosingTermEvent() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        InterestAwareEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)
                )
                .expectDispatchedCommandsEqualTo(
                        UnderChoosingTermEventCommands.Create.of(eventId, eventDetails)
                );
    }
}
