package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.builders.EnrollmentIdentifiedTermBuilder;
import knbit.events.bc.enrollment.domain.exceptions.EventUnderEnrollmentExceptions;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.eventready.builders.IdentifiedTermWithAttendeeBuilder;
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

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventUnderEnrollmentFixtureConfiguration();
        eventId = EventId.of("id");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
        identifiedTerm = EnrollmentIdentifiedTermBuilder.defaultTerm();
        lecturers = ImmutableList.of(Lecturer.of("firstName lastName", null));
    }

    @Test
    public void shouldProduceTransitionEventWithRequiredData() throws Exception {

        final MemberId member1 = MemberId.of("member1");
        final MemberId member2 = MemberId.of("member2");

        final IdentifiedTermWithAttendees expectedTerm = IdentifiedTermWithAttendees.of(
                identifiedTerm.termId(),
                identifiedTerm.duration(),
                identifiedTerm.participantsLimit(),
                identifiedTerm.location(),
                ImmutableList.of(Lecturer.of("John Doe", "john-doe")),
                ImmutableList.of(Attendee.of(member1), Attendee.of(member2))
        );

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
                                ImmutableList.of(expectedTerm)
                        )
                );
    }

}