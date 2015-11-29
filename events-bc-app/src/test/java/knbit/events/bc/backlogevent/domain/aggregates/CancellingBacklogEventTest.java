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
public class CancellingBacklogEventTest {

    private FixtureConfiguration<BacklogEvent> fixture;
    private EventId eventId = EventId.of("id");
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.backlogEventFixtureConfiguration();
        eventDetails = EventDetailsBuilder.defaultEventDetails();
    }

    @Test
    public void shouldBeAbleToCancelCreatedBacklogEvent() throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        BacklogEventCommands.Cancel.of(eventId)
                )
                .expectEvents(
                        BacklogEventEvents.Cancelled.of(eventId)
                );
    }

    @Test
    public void shouldNotBeAbleToCancelTransitedEvent() throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, eventDetails),
                        BacklogEventTransitionEvents.TransitedToInterestAware.of(eventId, eventDetails)
                )
                .when(
                        BacklogEventCommands.Cancel.of(eventId)
                )
                .expectException(
                        IllegalStateException.class
                );
    }

    @Test
    public void shouldNotBeAbleToCancelAlreadyCancelledEvent() throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, eventDetails),
                        BacklogEventEvents.Cancelled.of(eventId)
                )
                .when(
                        BacklogEventCommands.Cancel.of(eventId)
                )
                .expectException(
                        IllegalStateException.class
                );
    }
}
