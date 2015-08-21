package knbit.events.bc.choosingterm.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.BookRoomCommand;
import knbit.events.bc.choosingterm.domain.valuobjects.events.RoomRequestedEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventCreated;
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
                        UnderChoosingTermEventCreated.of(eventId, eventDetails)
                )
                .when(
                        BookRoomCommand.of(eventId, eventDuration.start(), eventDuration.duration(), capacity.value())
                )
                .expectEventsMatching(
                        matchExactlyIgnoring(
                                "reservationId",
                                ImmutableList.of(
                                        RoomRequestedEvent.of(eventId, ReservationId.of("ignored"), eventDuration, capacity)
                                )
                        )
                );
    }
}
