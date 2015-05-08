package knbit.events.bc;

import knbit.events.bc.common.domain.ICommandHandler;
import org.axonframework.eventsourcing.AbstractEventSourcedAggregateRoot;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;

public abstract class ARTestBase<T extends AbstractEventSourcedAggregateRoot> {

    protected abstract Class<T> getAggregateType();
    protected abstract AggregateFactory<T> getAggregateFactory();
    protected abstract ICommandHandler<T> getCommandHandler();
    protected FixtureConfiguration fixture;

    @Before
    public void configureTestFixture() {
        AggregateFactory<T> aggregateFactory = getAggregateFactory();
        ICommandHandler<T> commandHandler = getCommandHandler();

        fixture = Fixtures.newGivenWhenThenFixture(getAggregateType());
        fixture.registerAggregateFactory(aggregateFactory);
        fixture.registerAnnotatedCommandHandler(commandHandler);

        commandHandler.setRepository(fixture.getRepository());
    }

}