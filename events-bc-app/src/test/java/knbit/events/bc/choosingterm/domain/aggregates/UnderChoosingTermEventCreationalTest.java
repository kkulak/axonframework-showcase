package knbit.events.bc.choosingterm.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.*;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 16.08.15.
 */
public class UnderChoosingTermEventCreationalTest {

    private FixtureConfiguration<UnderChoosingTermEvent> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.underChoosingTermEventFixtureConfiguration();
    }

    @Test
    public void shouldProduceUnderChoosingTermEventCreatedEventGivenCorrespondingCommand() throws Exception {

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
                        UnderChoosingTermEventCommands.Create.of(eventId, eventDetails)
                )
                .expectEvents(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                );

    }
}