package knbit.events.bc.eventready.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.eventready.builders.EventReadyDetailsBuilder;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;
import knbit.events.bc.eventready.domain.valueobjects.ReadyCommands;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by novy on 31.10.15.
 */
public class NotifyEventTookPlaceSagaTest {

    private AnnotatedSagaTestFixture fixture;

    private ReadyEventId readyEventId;
    private EventId correlationId;
    private EventReadyDetails details;
    private Collection<Attendee> attendees;

    @Before
    public void setUp() throws Exception {
        fixture = new AnnotatedSagaTestFixture(NotifyEventTookPlaceSaga.class);

        readyEventId = ReadyEventId.of("id");
        correlationId = EventId.of("id");
        details = EventReadyDetailsBuilder.defaultEventDetails();
        attendees = ImmutableList.of(Attendee.of(MemberId.of("member")));
    }

    @Test
    public void shouldScheduleReadyEventEndedOnEventCreation() throws Exception {
        final DateTime scheduledAt = details.duration().end();

        fixture
                .givenNoPriorActivity()
                .whenAggregate(readyEventId)
                .publishes(
                        ReadyEvents.Created.of(readyEventId, correlationId, details, attendees)
                )
                .expectScheduledEvent(
                        scheduledAt,
                        ReadyEventEnded.of(readyEventId)
                );
    }

    @Test
    public void shouldSendCommandToMarkAggregateItTookPlaceIfEventEnded() throws Exception {
        final DateTime endingDate = details.duration().end();

        fixture
                .givenAggregate(readyEventId)
                .published(
                        ReadyEvents.Created.of(readyEventId, correlationId, details, attendees)
                )
                .whenTimeAdvancesTo(endingDate)
                .expectDispatchedCommandsEqualTo(
                        ReadyCommands.MarkTookPlace.of(readyEventId)
                );
    }

    @Test
    public void shouldEndIfEventEnded() throws Exception {
        final DateTime endingDate = details.duration().end();

        fixture
                .givenAggregate(readyEventId)
                .published(
                        ReadyEvents.Created.of(readyEventId, correlationId, details, attendees)
                )
                .whenTimeAdvancesTo(endingDate)
                .expectActiveSagas(0);
    }

    @Test
    public void shouldEndIfReadyEventCancelled() throws Exception {
        fixture
                .givenAggregate(readyEventId)
                .published(
                        ReadyEvents.Created.of(readyEventId, correlationId, details, attendees)
                )
                .whenAggregate(readyEventId)
                .publishes(
                        ReadyEvents.Cancelled.of(readyEventId, Collections.emptyList())
                )
                .expectActiveSagas(0);
    }
}
