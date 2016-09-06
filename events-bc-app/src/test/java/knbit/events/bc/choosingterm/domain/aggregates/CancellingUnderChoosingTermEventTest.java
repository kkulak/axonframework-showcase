package knbit.events.bc.choosingterm.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.exceptions.UnderChoosingTermEventExceptions;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.builders.EnrollmentIdentifiedTermBuilder;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 22.11.15.
 */
public class CancellingUnderChoosingTermEventTest {

    private FixtureConfiguration<UnderChoosingTermEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.underChoosingTermEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
    }

    @Test
    public void shouldNotBeAbleToCancelTransitedEvent() throws Exception {
        final ImmutableList<EnrollmentIdentifiedTerm> terms = ImmutableList.of(
                EnrollmentIdentifiedTermBuilder.defaultTerm()
        );

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(eventId, eventDetails, terms)
                )
                .when(
                        UnderChoosingTermEventCommands.Cancel.of(eventId)

                )
                .expectException(
                        UnderChoosingTermEventExceptions.AlreadyTransitedToEnrollment.class
                );
    }

    @Test
    public void shouldNotBeAbleToCancelTwice() throws Exception {

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        UnderChoosingTermEventEvents.Cancelled.of(eventId)
                )
                .when(
                        UnderChoosingTermEventCommands.Cancel.of(eventId)
                )
                .expectException(
                        UnderChoosingTermEventExceptions.AlreadyCancelled.class
                );
    }

    @Test
    public void shouldCancelEvent() throws Exception {

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        UnderChoosingTermEventCommands.Cancel.of(eventId)
                )
                .expectEvents(
                        UnderChoosingTermEventEvents.Cancelled.of(eventId)
                );
    }

    @Test
    public void shouldCancelPendingReservationsIfAny() throws Exception {
        final ReservationEvents.RoomRequested firstRoomRequested = ReservationEvents.RoomRequested.of(
                eventId,
                ReservationId.of("res1"),
                EventDuration.of(DateTime.now(), Duration.standardHours(1)),
                Capacity.of(50)
        );

        final ReservationEvents.RoomRequested secondRoomRequested = ReservationEvents.RoomRequested.of(
                eventId,
                ReservationId.of("res2"),
                EventDuration.of(DateTime.now(), Duration.standardHours(1)),
                Capacity.of(50)
        );

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        firstRoomRequested,
                        secondRoomRequested
                )
                .when(
                        UnderChoosingTermEventCommands.Cancel.of(eventId)
                )
                .expectEvents(
                        ReservationEvents.ReservationCancelled.of(eventId, ReservationId.of("res1")),
                        ReservationEvents.ReservationCancelled.of(eventId, ReservationId.of("res2")),

                        UnderChoosingTermEventEvents.Cancelled.of(eventId)
                );
    }
}
