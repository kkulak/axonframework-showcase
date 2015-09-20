package knbit.events.bc.choosingterm.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.ReservationCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

public class FailingReservationTest {
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
    public void shouldNotBeAbleToFailNotExistingReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        ReservationCommands.FailReservation.of(eventId, ReservationId.of("fakeId"), "fake")
                )
                .expectException(ReservationExceptions.ReservationDoesNotExist.class);
    }

    @Test
    public void shouldNotBeAbleToFailAlreadyAcceptedReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationAccepted.of(eventId, reservationId)
                )
                .when(
                        ReservationCommands.FailReservation.of(eventId, reservationId, "fail")
                )
                .expectException(ReservationExceptions.ReservationAcceptedException.class);
    }

    @Test
    public void shouldNotBeAbleToFailAlreadyRejectedReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationRejected.of(eventId, reservationId)
                )
                .when(
                        ReservationCommands.FailReservation.of(eventId, reservationId, "fail")
                )
                .expectException(ReservationExceptions.ReservationRejectedException.class);
    }

    @Test
    public void shouldNotBeAbleToFailAlreadyCancelledReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationCancelled.of(eventId, reservationId)
                )
                .when(
                        ReservationCommands.FailReservation.of(eventId, reservationId, "fail")
                )
                .expectException(ReservationExceptions.ReservationCancelledException.class);
    }

    @Test
    public void shouldNotBeAbleToFailAlreadyFailedReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationFailed.of(eventId, reservationId, "fail")
                )
                .when(
                        ReservationCommands.FailReservation.of(eventId, reservationId, "fail")
                )
                .expectException(ReservationExceptions.ReservationFailedException.class);
    }

    @Test
    public void shouldGenerateReservationFailedEventOtherwise() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity)
                )
                .when(
                        ReservationCommands.FailReservation.of(eventId, reservationId, "fail")
                )
                .expectEvents(
                        ReservationEvents.ReservationFailed.of(eventId, reservationId, "fail")
                );
    }

}
