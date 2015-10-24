package knbit.events.bc.eventready.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import knbit.events.bc.eventready.builders.IdentifiedTermWithAttendeeBuilder;
import knbit.events.bc.eventready.domain.valueobjects.ReadyCommands;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by novy on 24.10.15.
 */
public class ReadyEventCreationalTest {

    private FixtureConfiguration<ReadyEvent> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.readyEventFixtureConfiguration();
    }

    @Test
    public void shouldProduceCreatedEventOnCorrespondingCreateCommand() throws Exception {

        final EventId eventId = EventId.of("eventId");
        final EventDetails eventDetails = EventDetailsBuilder.defaultEventDetails();
        final Collection<IdentifiedTermWithAttendees> soleTerm =
                ImmutableList.of(IdentifiedTermWithAttendeeBuilder.defaultTerm());


        fixture
                .givenNoPriorActivity()
                .when(
                        ReadyCommands.Create.of(eventId, eventDetails, soleTerm)
                )
                .expectEvents(
                        ReadyEvents.Created.of(eventId, eventDetails, soleTerm)
                );
    }
}