package knbit.events.bc.backlogevent.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.CreateBacklogEventCommand;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.TransitBacklogEventToInterestAwareEventCommand;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.TransitBacklogEventToUnderChoosingTermEventCommand;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventCreated;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitedToInterestAwareEvent;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitedToUnderChoosingTermEvent;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class BacklogEventTest {
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
    public void shouldCreateBacklogEventGivenCreateBacklogEventCommand() throws Exception {
        fixture
                .given()
                .when(
                        CreateBacklogEventCommand.of(eventId, eventDetails)
                )
                .expectEvents(
                        BacklogEventCreated.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldTransitToInterestAwareEventGivenCorrespondingCommand() throws Exception {
        fixture
                .given(
                        BacklogEventCreated.of(eventId, eventDetails)
                )
                .when(
                        TransitBacklogEventToInterestAwareEventCommand.of(eventId)
                )
                .expectEvents(
                        BacklogEventTransitedToInterestAwareEvent.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldNotBeAbleToTransitBacklogEventToInterestAwareEventMoreThanOnce() throws Exception {
        fixture
                .given(
                        BacklogEventCreated.of(eventId, eventDetails),
                        BacklogEventTransitedToInterestAwareEvent.of(eventId, eventDetails)
                )
                .when(
                        TransitBacklogEventToInterestAwareEventCommand.of(eventId)
                )
                .expectException(IllegalStateException.class);
    }

    @Test
    public void shouldTransitToUnderChoosingTermEventGivenCorrespondingCommand() throws Exception {
        fixture
                .given(
                        BacklogEventCreated.of(eventId, eventDetails)
                )
                .when(
                        TransitBacklogEventToUnderChoosingTermEventCommand.of(eventId)
                )
                .expectEvents(
                        BacklogEventTransitedToUnderChoosingTermEvent.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldNotBeAbleToTransitBacklogEventToUnderChoosingTermEventMoreThanOnce() throws Exception {
        fixture
                .given(
                        BacklogEventCreated.of(eventId, eventDetails),
                        BacklogEventTransitedToUnderChoosingTermEvent.of(eventId, eventDetails)
                )
                .when(
                        TransitBacklogEventToInterestAwareEventCommand.of(eventId)
                )
                .expectException(IllegalStateException.class);
    }

}
