package knbit.events.bc.choosingterm.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.exceptions.UnderChoosingTermEventExceptions;
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
public class RequestingRoomTest {

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
    public void shouldProduceRoomRequestedEventWithNewlyCreatedReservationId() throws Exception {
        final EventDuration eventDuration = EventDuration.of(DateTime.now(), Duration.standardMinutes(90));
        final Capacity capacity = Capacity.of(66);
        final ReservationId randomlyGeneratedReservationId = ReservationId.of("id");
        makeIdFactoryReturn(randomlyGeneratedReservationId);

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        ReservationCommands.BookRoom.of(eventId, eventDuration.start(), eventDuration.duration(), capacity.value())
                )
                .expectEvents(
                        ReservationEvents.RoomRequested.of(eventId, randomlyGeneratedReservationId, eventDuration, capacity)
                );
    }

    @Test
    public void shouldNotBeAbleToRequestRoomIfEventTransitedToEnrollment() throws Exception {

        final Term term = TermBuilder.defaultTerm();
        final TermId termId = TermId.of("termId");

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),

                        TermEvents.TermAdded.of(eventId, termId, term),

                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(IdentifiedTerm.of(termId, term))
                        )
                )
                .when(
                        ReservationCommands.BookRoom.of(eventId, DateTime.now(), Duration.standardHours(1), 100)
                )
                .expectException(
                        UnderChoosingTermEventExceptions.AlreadyTransitedToEnrollment.class
                );
    }

    private void makeIdFactoryReturn(ReservationId reservationId) {
        PowerMockito.mockStatic(IdFactory.class);
        Mockito.when(IdFactory.reservationId()).thenReturn(reservationId);
    }
}
