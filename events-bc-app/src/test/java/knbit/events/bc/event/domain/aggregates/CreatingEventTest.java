package knbit.events.bc.event.domain.aggregates;

import knbit.events.bc.ARTestBase;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.ICommandHandler;
import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.event.domain.EventCommandHandler;
import knbit.events.bc.event.domain.builders.CreateEventCommandBuilder;
import knbit.events.bc.event.domain.builders.CyclicEventCreatedBuilder;
import knbit.events.bc.event.domain.builders.OneOffEventCreatedBuilder;
import knbit.events.bc.event.domain.enums.EventState;
import knbit.events.bc.event.domain.valueobjects.Description;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.valueobjects.Name;
import knbit.events.bc.event.domain.valueobjects.events.OneOffEventCreated;
import knbit.events.bc.event.infrastructure.config.AxonEventFactory;
import org.axonframework.eventsourcing.AggregateFactory;
import org.junit.Before;
import org.junit.Test;

public class CreatingEventTest extends ARTestBase<AbstractEvent> {

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

    // Configuration
    @Override
    protected Class<AbstractEvent> getAggregateType() {
        return AbstractEvent.class;
    }

    @Override
    protected AggregateFactory<AbstractEvent> getAggregateFactory() {
        return new AxonEventFactory();
    }

    @Override
    protected ICommandHandler<AbstractEvent> getCommandHandler() {
        return new EventCommandHandler();
    }

}
