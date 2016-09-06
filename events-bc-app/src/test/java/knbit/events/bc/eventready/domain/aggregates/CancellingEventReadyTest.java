package knbit.events.bc.eventready.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.eventready.builders.EventReadyDetailsBuilder;
import knbit.events.bc.eventready.domain.EventReadyExceptions;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;
import knbit.events.bc.eventready.domain.valueobjects.ReadyCommands;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by novy on 22.11.15.
 */
public class CancellingEventReadyTest {

    private FixtureConfiguration<ReadyEvent> fixture;
    private EventId correlationId;
    private ReadyEventId eventId;
    private EventReadyDetails eventReadyDetails;
    private Collection<Attendee> attendees;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.readyEventFixtureConfiguration();
        correlationId = EventId.of("correlationId");
        eventId = ReadyEventId.of("id");
        eventReadyDetails = EventReadyDetailsBuilder.defaultEventDetails();
        attendees = ImmutableList.of(
                Attendee.of(MemberId.of("member1")),
                Attendee.of(MemberId.of("member1"))
        );
    }

    @Test
    public void shouldNotBeAbleToCancelTwice() throws Exception {
        fixture
                .given(
                        ReadyEvents.Created.of(eventId, correlationId, eventReadyDetails, attendees),

                        ReadyEvents.Cancelled.of(eventId, eventReadyDetails, attendees)

                )
                .when(
                        ReadyCommands.Cancel.of(eventId)
                )
                .expectException(EventReadyExceptions.AlreadyCancelled.class);
    }

    @Test
    public void shouldNotBeAbleToCancelIfAlreadyTookPlace() throws Exception {
        fixture
                .given(
                        ReadyEvents.Created.of(eventId, correlationId, eventReadyDetails, attendees),
                        ReadyEvents.TookPlace.of(eventId, eventReadyDetails, attendees)

                )
                .when(
                        ReadyCommands.Cancel.of(eventId)
                )
                .expectException(EventReadyExceptions.AlreadyMarkedItTookPlace.class);
    }

    @Test
    public void shouldBeAbleToCancelReadyEvent() throws Exception {
        fixture
                .given(
                        ReadyEvents.Created.of(eventId, correlationId, eventReadyDetails, attendees)
                )
                .when(
                        ReadyCommands.Cancel.of(eventId)
                )
                .expectEvents(
                        ReadyEvents.Cancelled.of(eventId, eventReadyDetails, attendees)
                );
    }
}
