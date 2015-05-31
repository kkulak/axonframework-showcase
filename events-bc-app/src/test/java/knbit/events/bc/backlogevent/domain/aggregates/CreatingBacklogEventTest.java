package knbit.events.bc.backlogevent.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.backlogevent.domain.builders.BacklogEventCreatedBuilder;
import knbit.events.bc.backlogevent.domain.builders.BacklogEventDeactivatedBuilder;
import knbit.events.bc.backlogevent.domain.builders.CreateBacklogEventCommandBuilder;
import knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventState;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.DeactivateBacklogEventCommand;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventDeactivated;
import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import static knbit.events.bc.backlogevent.domain.valueobjects.BacklogEventState.*;
import static knbit.events.bc.common.domain.enums.EventFrequency.*;
import static knbit.events.bc.common.domain.enums.EventType.*;

public class CreatingBacklogEventTest {
    private FixtureConfiguration<BacklogEvent> fixture;
    private EventId eventId = EventId.of("id");

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.backlogEventFixtureConfiguration();
    }

    @Test
    public void shouldCreateBacklogEventGivenCreateBacklogEventCommand() throws Exception {
        fixture.given()
                .when(
                        CreateBacklogEventCommandBuilder
                                .newCreateBacklogEventCommand()
                                .build()
                )
                .expectEvents(
                        BacklogEventCreatedBuilder
                                .instance()
                                .build()
                );
    }

    @Test
    public void shouldDeactivateBacklogEventGivenDeactivateBacklogEventCommand() throws Exception {
        fixture.given(
                    BacklogEventCreatedBuilder
                    .instance()
                    .build()
                )
                .when(
                        DeactivateBacklogEventCommand.of(eventId)
                )
                .expectEvents(
                        BacklogEventDeactivatedBuilder
                            .instance()
                            .build()
                );
    }

    @Test
    public void shouldThrowExceptionGivenDeactivateBacklogEventCommandOnAlreadyInactivatedBacklogEvent() throws Exception {
        fixture.given(
                BacklogEventCreatedBuilder
                        .instance()
                        .build(),
                BacklogEventDeactivatedBuilder
                        .instance()
                        .build()
                )
                .when(
                        DeactivateBacklogEventCommand.of(eventId)
                )
                .expectException(IllegalStateException.class);
    }

}
