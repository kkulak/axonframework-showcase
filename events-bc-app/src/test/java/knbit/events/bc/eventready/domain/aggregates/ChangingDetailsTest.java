package knbit.events.bc.eventready.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
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
 * Created by novy on 29.11.15.
 */
public class ChangingDetailsTest {

    private FixtureConfiguration<ReadyEvent> fixture;
    private EventId correlationId;
    private ReadyEventId eventId;
    private EventReadyDetails oldDetails;
    private Collection<Attendee> attendees;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.readyEventFixtureConfiguration();
        correlationId = EventId.of("correlationId");
        eventId = ReadyEventId.of("id");
        oldDetails = EventReadyDetailsBuilder.defaultEventDetails();
        attendees = ImmutableList.of(
                Attendee.of(MemberId.of("member1")),
                Attendee.of(MemberId.of("member1"))
        );
    }

    @Test
    public void shouldBeAbleToChangeDetails() throws Exception {
        final EventReadyDetails withDifferentParticipantsLimit =
                EventReadyDetailsBuilder.instance().limit(ParticipantsLimit.of(6666)).build();

        fixture
                .given(ReadyEvents.Created.of(eventId, correlationId, oldDetails, attendees))
                .when(ReadyCommands.ChangeDetails.of(eventId, withDifferentParticipantsLimit))
                .expectEvents(ReadyEvents.DetailsChanged.of(eventId, oldDetails, withDifferentParticipantsLimit));
    }

    @Test
    public void shouldNotChangeDetailsIfEventCancelled() throws Exception {
        fixture
                .given(
                        ReadyEvents.Created.of(eventId, correlationId, oldDetails, attendees),
                        ReadyEvents.Cancelled.of(eventId, attendees)
                )
                .when(ReadyCommands.ChangeDetails.of(eventId, EventReadyDetailsBuilder.defaultEventDetails()))
                .expectException(EventReadyExceptions.AlreadyCancelled.class);
    }

    @Test
    public void shouldNotChangeDetailsIfEventTookPlace() throws Exception {
        fixture
                .given(
                        ReadyEvents.Created.of(eventId, correlationId, oldDetails, attendees),
                        ReadyEvents.TookPlace.of(eventId, oldDetails, attendees)
                )
                .when(ReadyCommands.ChangeDetails.of(eventId, EventReadyDetailsBuilder.defaultEventDetails()))
                .expectException(EventReadyExceptions.AlreadyMarkedItTookPlace.class);
    }
}
