package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.*;
import knbit.events.bc.interest.domain.valueobjects.commands.InterestAwareEventCommands;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
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
        final EventDetails eventDetails = EventDetails.of(
                Name.of("name"),
                Description.of("desc"),
                EventType.WORKSHOP,
                URL.of("https://www.google.pl/"),
                Section.of("0", "Idea Factory")
        );


        fixture
                .givenNoPriorActivity()
                .when(
                        InterestAwareEventCommands.Create.of(eventId, eventDetails)
                )
                .expectEvents(
                        InterestAwareEvents.Created.of(eventId, eventDetails)
                );

    }
}