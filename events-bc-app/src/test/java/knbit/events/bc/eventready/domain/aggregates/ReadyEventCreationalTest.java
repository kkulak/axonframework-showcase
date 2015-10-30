package knbit.events.bc.eventready.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.eventready.builders.EventReadyDetailsBuilder;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;
import knbit.events.bc.eventready.domain.valueobjects.ReadyCommands;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

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

        final EventId eventId = EventId.of("eventReadyId");
        final EventId correlationId = EventId.of("correlationId");
        final EventReadyDetails eventDetails = EventReadyDetailsBuilder.defaultEventDetails();
        final ImmutableList<Attendee> attendees = ImmutableList.of(
                Attendee.of(MemberId.of("member1")),
                Attendee.of(MemberId.of("member1"))
        );

        fixture
                .givenNoPriorActivity()
                .when(
                        ReadyCommands.Create.of(eventId, correlationId, eventDetails, attendees)
                )
                .expectEvents(
                        ReadyEvents.Created.of(eventId, correlationId, eventDetails, attendees)
                );
    }
}