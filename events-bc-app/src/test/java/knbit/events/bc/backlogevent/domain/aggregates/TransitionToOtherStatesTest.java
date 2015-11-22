package knbit.events.bc.backlogevent.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitionEvents;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 20.11.15.
 */
public class TransitionToOtherStatesTest {

    private FixtureConfiguration<BacklogEvent> fixture;
    private EventId eventId = EventId.of("id");
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.backlogEventFixtureConfiguration();
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
    }

    @Test
    public void shouldTransitToInterestAwareEventGivenCorrespondingCommand() throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        BacklogEventCommands.TransitToInterestAwareEventCommand.of(eventId)
                )
                .expectEvents(
                        BacklogEventTransitionEvents.TransitedToInterestAware.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldNotBeAbleToTransitBacklogEventToInterestAwareEventMoreThanOnce() throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, eventDetails),
                        BacklogEventTransitionEvents.TransitedToInterestAware.of(eventId, eventDetails)
                )
                .when(
                        BacklogEventCommands.TransitToInterestAwareEventCommand.of(eventId)
                )
                .expectException(IllegalStateException.class);
    }

    @Test
    public void shouldNotBeAbleToTransitToInterestAwareEventIfCancelled() throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, eventDetails),
                        BacklogEventEvents.Cancelled.of(eventId)
                )
                .when(
                        BacklogEventCommands.TransitToInterestAwareEventCommand.of(eventId)
                )
                .expectException(IllegalStateException.class);
    }

    @Test
    public void shouldTransitToUnderChoosingTermEventGivenCorrespondingCommand() throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        BacklogEventCommands.TransitToUnderChoosingTermEventCommand.of(eventId)
                )
                .expectEvents(
                        BacklogEventTransitionEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldNotBeAbleToTransitToChoosingTermEventIfCancelled() throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, eventDetails),
                        BacklogEventEvents.Cancelled.of(eventId)
                )
                .when(
                        BacklogEventCommands.TransitToUnderChoosingTermEventCommand.of(eventId)
                )
                .expectException(IllegalStateException.class);
    }

    @Test
    public void shouldNotBeAbleToTransitBacklogEventToUnderChoosingTermEventMoreThanOnce() throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, eventDetails),
                        BacklogEventTransitionEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)
                )
                .when(
                        BacklogEventCommands.TransitToInterestAwareEventCommand.of(eventId)
                )
                .expectException(IllegalStateException.class);
    }
}
