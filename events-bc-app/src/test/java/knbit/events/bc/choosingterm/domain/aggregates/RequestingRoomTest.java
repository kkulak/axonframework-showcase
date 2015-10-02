package knbit.events.bc.choosingterm.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.exceptions.UnderChoosingTermEventExceptions;
import knbit.events.bc.choosingterm.domain.valuobjects.*;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.ReservationCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

import static knbit.events.bc.matchers.WithoutIdentifierMatcher.matchExactlyIgnoring;

/**
 * Created by novy on 19.08.15.
 */
public class RequestingRoomTest {

    private FixtureConfiguration<UnderChoosingTermEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.underChoosingTermEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
    }

    @Test
    public void shouldProduceRoomRequestedEventWithNewlyCreatedReservationId() throws Exception {
        final EventDuration eventDuration = EventDuration.of(DateTime.now(), Duration.standardMinutes(90));
        final Capacity capacity = Capacity.of(66);

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        ReservationCommands.BookRoom.of(eventId, eventDuration.start(), eventDuration.duration(), capacity.value())
                )
                .expectEventsMatching(
                        matchExactlyIgnoring(
                                "reservationId",
                                ImmutableList.of(
                                        ReservationEvents.RoomRequested.of(eventId, ReservationId.of("ignored"), eventDuration, capacity)
                                )
                        )
                );
    }

    @Test
    public void shouldNotBeAbleToRequestRoomIfEventTransitedToEnrollment() throws Exception {

        final Term term = Term.of(
                EventDuration.of(DateTime.now(), Duration.standardMinutes(60)),
                Capacity.of(60),
                Location.of("3.27A")
        );

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),

                        TermEvents.TermAdded.of(eventId, term),

                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(term)
                        )
                )
                .when(
                        ReservationCommands.BookRoom.of(eventId, DateTime.now(), Duration.standardHours(1), 100)
                )
                .expectException(
                        UnderChoosingTermEventExceptions.AlreadyTransitedToEnrollment.class
                );
    }
}
