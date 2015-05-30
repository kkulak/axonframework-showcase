package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.commands.CreateInterestAwareEventCommand;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 30.05.15.
 */
public class InterestAwareEventCreationalTest {

    private FixtureConfiguration<InterestAwareEvent> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.interestAwareEventFixtureConfiguration();
    }

    @Test
    public void shouldProduceInterestAwareEventCreatedEventGivenCorrespondingCommand() throws Exception {

        final EventId eventId = EventId.of("eventId");
        final EventDetails eventDetails = new EventDetails(
                Name.of("name"),
                Description.of("desc"),
                EventType.WORKSHOP,
                EventFrequency.ONE_OFF
        );


        fixture
                .givenNoPriorActivity()
                .when(
                        new CreateInterestAwareEventCommand(eventId, eventDetails)
                )
                .expectEvents(
                        new InterestAwareEventCreated(eventId, eventDetails)
                );

    }
}