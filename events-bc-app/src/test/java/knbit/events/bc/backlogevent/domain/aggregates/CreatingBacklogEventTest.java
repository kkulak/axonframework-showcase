package knbit.events.bc.backlogevent.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.backlogevent.domain.builders.BacklogEventCreatedBuilder;
import knbit.events.bc.backlogevent.domain.builders.CreateBacklogEventCommandBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class CreatingBacklogEventTest {
    private FixtureConfiguration<BacklogEvent> fixture;

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

}
