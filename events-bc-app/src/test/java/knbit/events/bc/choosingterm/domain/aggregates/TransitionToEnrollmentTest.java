package knbit.events.bc.choosingterm.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.exceptions.TransitionToEnrollmentExceptions;
import knbit.events.bc.choosingterm.domain.valuobjects.*;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
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

/**
 * Created by novy on 02.10.15.
 */
public class TransitionToEnrollmentTest {

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
    public void shouldNotBeAbleToTransitEventWithPendingReservations() throws Exception {
        final ReservationEvents.RoomRequested roomRequestedEvent = ReservationEvents.RoomRequested.of(
                eventId,
                ReservationId.of("id"),
                EventDuration.of(DateTime.now(), Duration.standardMinutes(90)),
                Capacity.of(10)
        );

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        roomRequestedEvent
                )
                .when(
                        UnderChoosingTermEventCommands.TransitToEnrollment.of(eventId)
                )
                .expectException(
                        TransitionToEnrollmentExceptions.HasPendingReservations.class
                );
    }

    @Test
    public void shouldNotBeAbleToTransitIfThereIsNoTermAssigned() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        UnderChoosingTermEventCommands.TransitToEnrollment.of(eventId)
                )
                .expectException(
                        TransitionToEnrollmentExceptions.DoesNotHaveAnyTerms.class
                );
    }

    @Test
    public void otherwiseItShouldProduceProperEvent() throws Exception {
        final Term soleTerm = Term.of(
                EventDuration.of(DateTime.now(), Duration.standardMinutes(60)),
                Capacity.of(60),
                Location.of("3.27A")
        );

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        TermEvents.TermAdded.of(eventId, soleTerm)
                )
                .when(
                        UnderChoosingTermEventCommands.TransitToEnrollment.of(eventId)
                )
                .expectEvents(
                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(soleTerm)
                        )
                );
    }
}
