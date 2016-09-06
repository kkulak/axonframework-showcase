package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.builders.EnrollmentIdentifiedTermBuilder;
import knbit.events.bc.enrollment.domain.exceptions.EventUnderEnrollmentExceptions;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by novy on 24.10.15.
 */
public class TransitionToReadyTest {

    private FixtureConfiguration<EventUnderEnrollment> fixture;
    private EventId eventId;
    private EventDetails eventDetails;
    private EnrollmentIdentifiedTerm identifiedTerm;
    private Collection<Lecturer> lecturers;
    private MemberId member1;
    private MemberId member2;
    private IdentifiedTermWithAttendees termWithAttendees;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventUnderEnrollmentFixtureConfiguration();
        eventId = EventId.of("id");
        eventDetails = EventDetailsBuilder.defaultEventDetails();

        identifiedTerm = EnrollmentIdentifiedTermBuilder.defaultTerm();
        lecturers = ImmutableList.of(Lecturer.of("John Doe", "john-doe"));
        member1 = MemberId.of("member1");
        member2 = MemberId.of("member2");

        termWithAttendees = IdentifiedTermWithAttendees.of(
                identifiedTerm.termId(),
                identifiedTerm.duration(),
                identifiedTerm.participantsLimit(),
                identifiedTerm.location(),
                lecturers,
                ImmutableList.of(Attendee.of(member1), Attendee.of(member2))
        );
    }

    @Test
    public void shouldProduceTransitionEventWithRequiredData() throws Exception {

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm)),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, identifiedTerm.termId(), member1),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, identifiedTerm.termId(), member2)
                )
                .when(
                        EventUnderEnrollmentCommands.TransitToReady.of(eventId)
                )
                .expectEvents(
                        EventUnderEnrollmentEvents.TransitedToReady.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(termWithAttendees)
                        )
                );
    }

    @Test
    public void shouldOnlyTransitOnce() throws Exception {

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm)),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, identifiedTerm.termId(), member1),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, identifiedTerm.termId(), member2),

                        EventUnderEnrollmentEvents.TransitedToReady.of(eventId, eventDetails, ImmutableList.of(termWithAttendees))
                )
                .when(
                        EventUnderEnrollmentCommands.TransitToReady.of(eventId)
                )
                .expectException(EventUnderEnrollmentExceptions.AlreadyTransitedToReady.class);
    }

    @Test
    public void shouldNotTransitIfCancelled() throws Exception {

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm)),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, identifiedTerm.termId(), member1),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, identifiedTerm.termId(), member2),

                        EventUnderEnrollmentEvents.Cancelled.of(eventId, eventDetails, ImmutableList.of(termWithAttendees))
                )
                .when(
                        EventUnderEnrollmentCommands.TransitToReady.of(eventId)
                )
                .expectException(EventUnderEnrollmentExceptions.AlreadyCancelled.class);
    }
}
