package knbit.events.bc.choosingterm.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions.ReservationAcceptedException;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions.ReservationCancelledException;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions.ReservationDoesNotExist;
import knbit.events.bc.choosingterm.domain.exceptions.ReservationExceptions.ReservationRejectedException;
import knbit.events.bc.choosingterm.domain.valuobjects.*;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.ReservationCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.IdFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


/**
 * Created by novy on 19.08.15.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(IdFactory.class)
public class AcceptingReservationTest {

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
        eventDetails = EventDetailsBuilder.defaultEventDetails();

        reservationId = ReservationId.of("reservationId");
        eventDuration = EventDuration.of(DateTime.now(), Duration.standardHours(2));
        capacity = Capacity.of(20);
    }

    @Test
    public void shouldNotBeAbleToAcceptNotExistingReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        ReservationCommands.AcceptReservation.of(eventId, ReservationId.of("fakeId"), "3.21c")
                )
                .expectException(ReservationDoesNotExist.class);
    }

    @Test
    public void shouldNotBeAbleToAcceptAlreadyAcceptedReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationAccepted.of(eventId, reservationId)
                )
                .when(
                        ReservationCommands.AcceptReservation.of(eventId, reservationId, "3.21c")
                )
                .expectException(ReservationAcceptedException.class);
    }

    @Test
    public void shouldNotBeAbleToAcceptAlreadyRejectedReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationRejected.of(eventId, reservationId)
                )
                .when(
                        ReservationCommands.AcceptReservation.of(eventId, reservationId, "3.21c")
                )
                .expectException(ReservationRejectedException.class);
    }

    @Test
    public void shouldNotBeAbleToAcceptAlreadyFailedReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationFailed.of(eventId, reservationId, "fail")
                )
                .when(
                        ReservationCommands.AcceptReservation.of(eventId, reservationId, "3.21c")
                )
                .expectException(ReservationExceptions.ReservationFailedException.class);
    }

    @Test
    public void shouldNotBeAbleToAcceptCancelledReservation() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity),
                        ReservationEvents.ReservationCancelled.of(eventId, reservationId)
                )
                .when(
                        ReservationCommands.AcceptReservation.of(eventId, reservationId, "3.21c")
                )
                .expectException(ReservationCancelledException.class);
    }

    @Test
    public void otherwiseShouldGenerateReservationAcceptedEventAndAddNewTerm() throws Exception {
        final Term termFromReservation = Term.of(eventDuration, capacity, Location.of("3.21c"));
        final TermId randomlyGeneratedTermId = TermId.of("id");
        makeIdFactoryReturn(randomlyGeneratedTermId);

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity)
                )
                .when(
                        ReservationCommands.AcceptReservation.of(eventId, reservationId, "3.21c")
                )
                .expectEvents(
                        ReservationEvents.ReservationAccepted.of(eventId, reservationId),
                        TermEvents.TermAdded.of(eventId, randomlyGeneratedTermId, termFromReservation)
                );
    }

    private void makeIdFactoryReturn(TermId termId) {
        PowerMockito.mockStatic(IdFactory.class);
        Mockito.when(IdFactory.termId()).thenReturn(termId);
    }
}
