package knbit.events.bc;

import knbit.events.bc.event.domain.EventCommandHandler;
import knbit.events.bc.event.domain.aggregates.AbstractEvent;
import knbit.events.bc.event.infrastructure.config.AxonEventFactory;
import knbit.events.bc.eventproposal.domain.EventProposalCommandHandler;
import knbit.events.bc.eventproposal.domain.aggregates.EventProposal;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;

/**
 * Created by novy on 05.05.15.
 */
public class FixtureFactory {

    public static FixtureConfiguration<EventProposal> eventProposalFixtureConfiguration() {
        FixtureConfiguration<EventProposal> fixture = Fixtures.newGivenWhenThenFixture(EventProposal.class);

        EventProposalCommandHandler commandHandler = new EventProposalCommandHandler(
                fixture.getRepository()
        );

        fixture.registerAnnotatedCommandHandler(commandHandler);
        return fixture;
    }

    public static FixtureConfiguration<AbstractEvent> eventFixtureConfiguration() {
        FixtureConfiguration<AbstractEvent> fixture = Fixtures.newGivenWhenThenFixture(AbstractEvent.class);
        fixture.registerAggregateFactory(new AxonEventFactory());
        final EventCommandHandler handler = new EventCommandHandler(fixture.getRepository());
        fixture.registerAnnotatedCommandHandler(handler);
        return fixture;
    }

}