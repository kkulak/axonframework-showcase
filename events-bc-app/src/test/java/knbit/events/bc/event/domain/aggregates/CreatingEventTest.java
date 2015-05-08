package knbit.events.bc.event.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.event.domain.builders.CreateEventCommandBuilder;
import knbit.events.bc.event.domain.builders.CyclicEventCreatedBuilder;
import knbit.events.bc.event.domain.builders.OneOffEventCreatedBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class CreatingEventTest {
    private FixtureConfiguration<AbstractEvent> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventFixtureConfiguration();
    }

    @Test
    public void shouldCreateOneOffEventGivenCreateEventOfOneOffFrequencyCommand() throws Exception {
        fixture.given()
                .when(
                        CreateEventCommandBuilder
                            .newCreateEventCommand()
                            .build()
                )
                .expectEvents(
                        OneOffEventCreatedBuilder
                            .instance()
                            .build()
                );
    }

    @Test
    public void shouldCreateCyclicEventGivenCreateEventOfCyclicFrequencyCommand() throws Exception {
        fixture.given()
                .when(
                        CreateEventCommandBuilder
                            .newCreateEventCommand()
                            .eventFrequency(EventFrequency.CYCLIC)
                            .build()
                )
                .expectEvents(
                        CyclicEventCreatedBuilder
                            .instance()
                            .build()
                );
    }

}
