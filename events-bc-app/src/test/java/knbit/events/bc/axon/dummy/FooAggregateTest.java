package knbit.events.bc.axon.dummy;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 05.05.15.
 */
public class FooAggregateTest {

    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(FooAggregate.class);

        FooCommandHandler commandHandler = new FooCommandHandler(
                fixture.getRepository()
        );
//            commandHandler.setEventProposalRepository(fixture.getRepository());


        fixture.registerAnnotatedCommandHandler(commandHandler);
    }

    @Test
    public void shouldCreateFoo() throws Exception {

        fixture.given()
                .when(
                        new CreateFooCommand(
                                new FooId("15"), "foo"
                        )
                )
                .expectEvents(
                        new FooCreatedEvent(
                                new FooId("15"), "foo"
                        )
                );

    }
}