package knbit.events.bc.backlogevent.domain.aggregates;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitionEvents;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by novy on 28.11.15.
 */
@RunWith(JUnitParamsRunner.class)
public class ChangingDetailsTest {

    private FixtureConfiguration<BacklogEvent> fixture;
    private EventId eventId = EventId.of("id");
    private EventDetails oldEventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.backlogEventFixtureConfiguration();
        oldEventDetails = EventDetailsBuilder.defaultEventDetails();
    }

    @Test
    public void shouldAllowToChangeDetails() throws Exception {

        final EventDetails newEventDetails = EventDetailsBuilder
                .instance()
                .description(Description.of("new desc!"))
                .build();

        fixture
                .given(BacklogEventEvents.Created.of(eventId, oldEventDetails))
                .when(BacklogEventCommands.ChangeDetails.of(eventId, newEventDetails))
                .expectEvents(BacklogEventEvents.EventDetailsChanged.of(eventId, oldEventDetails, newEventDetails));
    }

    @Test
    public void shouldNotBeAbleToChangeDetailsIfAlreadyCancelled() throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, oldEventDetails),
                        BacklogEventEvents.Cancelled.of(eventId)
                )
                .when(BacklogEventCommands.ChangeDetails.of(eventId, EventDetailsBuilder.defaultEventDetails()))
                .expectException(IllegalStateException.class);
    }

    @Test
    @Parameters(method = "transitionEvents")
    public void shouldNotBeAbleToChangeDetailsIfAlreadyTransited(BacklogEventTransitionEvents.TransitedToAnotherState transitionEvent) throws Exception {
        fixture
                .given(
                        BacklogEventEvents.Created.of(eventId, oldEventDetails),
                        transitionEvent
                )
                .when(BacklogEventCommands.ChangeDetails.of(eventId, EventDetailsBuilder.defaultEventDetails()))
                .expectException(IllegalStateException.class);
    }

    private Object[] transitionEvents() {
        return new Object[]{
                new Object[]{BacklogEventTransitionEvents.TransitedToInterestAware.of(eventId, oldEventDetails)},
                new Object[]{BacklogEventTransitionEvents.TransitedToUnderChoosingTerm.of(eventId, oldEventDetails)}
        };
    }


}
