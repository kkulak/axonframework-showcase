package knbit.events.bc.choosingterm.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.exceptions.TransitionToEnrollmentExceptions;
import knbit.events.bc.choosingterm.domain.exceptions.UnderChoosingTermEventExceptions;
import knbit.events.bc.choosingterm.domain.valuobjects.*;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.builders.TermClosureBuilder;
import knbit.events.bc.enrollment.domain.exceptions.EventUnderEnrollmentExceptions;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.enrollment.domain.valueobjects.TermClosure;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by novy on 02.10.15.
 */
public class TransitionToEnrollmentTest {

    private FixtureConfiguration<UnderChoosingTermEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;
    private List<TermClosure> termClosures;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.underChoosingTermEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
        termClosures = ImmutableList.of(TermClosureBuilder.defaultTerm());
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
                        UnderChoosingTermEventCommands.TransitToEnrollment.of(eventId, termClosures)
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
                        UnderChoosingTermEventCommands.TransitToEnrollment.of(eventId, termClosures)
                )
                .expectException(
                        TransitionToEnrollmentExceptions.DoesNotHaveAnyTerms.class
                );
    }

    @Test
    public void shouldNotBeAbleToTransitGivenIncompleteTermClosuresList() throws Exception {
        final TermId termId = TermId.of("another-term-id");
        final Term term = TermBuilder.defaultTerm();

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        TermEvents.TermAdded.of(eventId, termId, term)
                )
                .when(
                        UnderChoosingTermEventCommands.TransitToEnrollment.of(eventId, termClosures)
                )
                .expectException(
                        EventUnderEnrollmentExceptions.NoSuchTermException.class
                );
    }

    @Test
    public void shouldNotBeAbleToTransitIfCancelledBefore() throws Exception {
        final Term soleTerm = TermBuilder.defaultTerm();
        final TermId termId = TermId.of("term-id");

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        TermEvents.TermAdded.of(eventId, termId, soleTerm),
                        UnderChoosingTermEventEvents.Cancelled.of(eventId)
                )
                .when(
                        UnderChoosingTermEventCommands.TransitToEnrollment.of(eventId, termClosures)
                )
                .expectException(UnderChoosingTermEventExceptions.AlreadyCancelled.class);
    }

    @Test
    public void shouldProduceProperEventOnTransition() throws Exception {
        final Term soleTerm = TermBuilder.defaultTerm();
        final TermId termId = TermId.of("term-id");

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        TermEvents.TermAdded.of(eventId, termId, soleTerm)
                )
                .when(
                        UnderChoosingTermEventCommands.TransitToEnrollment.of(eventId, termClosures)
                )
                .expectEvents(
                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(
                                        EnrollmentIdentifiedTerm.of(
                                                termId, soleTerm,
                                                ImmutableList.of(Lecturer.of("John Doe", "john-doe")),
                                                ParticipantsLimit.of(10))
                                )
                        )
                );
    }

}
