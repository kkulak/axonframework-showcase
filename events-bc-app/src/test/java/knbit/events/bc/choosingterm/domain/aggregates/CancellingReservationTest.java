package knbit.events.bc.choosingterm.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions.ReservationAcceptedException;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions.ReservationCancelledException;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions.ReservationDoesNotExist;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions.ReservationRejectedException;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.ReservationCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.*;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by novy on 19.08.15.
 */
public class CancellingReservationTest {

    private FixtureConfiguration<UnderChoosingTermEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    private ReservationId reservationId;
    private EventDuration eventDuration;
    private Capacity capacity;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.underChoosingTermEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();

        reservationId = ReservationId.of("reservationId");
        eventDuration = EventDuration.of(DateTime.now(), Duration.standardDays(2));
        capacity = Capacity.of(20);
    }

    @Test
    public void shouldNotBeAbleToCancelNotExistingReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        ReservationCommands.CancelReservation.of(eventId, ReservationId.of("fakeId"))
                )
                .expectException(ReservationDoesNotExist.class);
    }

    @Test
    public void shouldNotBeAbleToCancelAlreadyAcceptedReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationAccepted.of(eventId, reservationId)
                )
                .when(
                        ReservationCommands.CancelReservation.of(eventId, reservationId)
                )
                .expectException(ReservationAcceptedException.class);
    }

    @Test
    public void shouldNotBeAbleToCancelAlreadyRejectedReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationRejected.of(eventId, reservationId)
                )
                .when(
                        ReservationCommands.CancelReservation.of(eventId, reservationId)
                )
                .expectException(ReservationRejectedException.class);
    }

    @Test
    public void shouldNotBeAbleToCancelAlreadyFailedReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationFailed.of(eventId, reservationId, "fail")
                )
                .when(
                        ReservationCommands.CancelReservation.of(eventId, reservationId)
                )
                .expectException(ReservationExceptions.ReservationFailedException.class);
    }

    @Test
    public void shouldNotBeAbleToCancelAlreadyCancelledReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationCancelled.of(eventId, reservationId)
                )
                .when(
                        ReservationCommands.CancelReservation.of(eventId, reservationId)
                )
                .expectException(ReservationCancelledException.class);
    }

    @Test
    public void shouldGenerateReservationCancelledEventOtherwise() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity)
                )
                .when(
                        ReservationCommands.CancelReservation.of(eventId, reservationId)
                )
                .expectEvents(
                        ReservationEvents.ReservationCancelled.of(eventId, reservationId)
                );
    }
}
