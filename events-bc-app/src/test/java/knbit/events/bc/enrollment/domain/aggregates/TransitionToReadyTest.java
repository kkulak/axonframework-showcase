package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.exceptions.EventUnderEnrollmentExceptions;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.TermModifyingEvents;
import knbit.events.bc.eventready.builders.IdentifiedTermWithAttendeeBuilder;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 24.10.15.
 */
public class TransitionToReadyTest {

    private FixtureConfiguration<EventUnderEnrollment> fixture;
    private EventId eventId;
    private EventDetails eventDetails;
    private IdentifiedTerm identifiedTerm;
    private Lecturer lecturer;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventUnderEnrollmentFixtureConfiguration();
        eventId = EventId.of("id");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
        identifiedTerm = IdentifiedTerm.of(TermId.of("id"), TermBuilder.defaultTerm());
        lecturer = Lecturer.of("firstName", "lastName");
    }

    @Test
    public void shouldNotTransitIfNoLecturerAssignedForTerm() throws Exception {
        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm))
                )
                .when(
                        EventUnderEnrollmentCommands.TransitToReady.of(eventId, eventDetails)
                )
                .expectException(
                        EventUnderEnrollmentExceptions.NoLecturerAssigned.class
                );
    }

    @Test
    public void shouldNotTransitTwice() throws Exception {

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm)),

                        TermModifyingEvents.LecturerAssigned.of(eventId, identifiedTerm.termId(), lecturer),

                        EventUnderEnrollmentEvents.TransitedToReady.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(IdentifiedTermWithAttendeeBuilder.defaultTerm())
                        )
                )
                .when(
                        EventUnderEnrollmentCommands.TransitToReady.of(eventId, eventDetails)
                )
                .expectException(
                        EventUnderEnrollmentExceptions.AlreadyTransitedToReady.class
                );
    }

    @Test
    public void otherwiseItShouldProduceTransitionEventWithRequiredData() throws Exception {

        final MemberId member1 = MemberId.of("member1");
        final MemberId member2 = MemberId.of("member2");

        final ParticipantsLimit newLimit =
                ParticipantsLimit.of(identifiedTerm.capacity().value() - 1);

        final IdentifiedTermWithAttendees expectedTerm = IdentifiedTermWithAttendees.of(
                identifiedTerm.termId(),
                identifiedTerm.duration(),
                newLimit,
                identifiedTerm.location(),
                lecturer,
                ImmutableList.of(Attendee.of(member1), Attendee.of(member2))
        );

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm)),

                        TermModifyingEvents.LecturerAssigned.of(eventId, identifiedTerm.termId(), lecturer),

                        TermModifyingEvents.ParticipantLimitSet.of(eventId, identifiedTerm.termId(), newLimit),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, identifiedTerm.termId(), member1),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, identifiedTerm.termId(), member2)
                )
                .when(
                        EventUnderEnrollmentCommands.TransitToReady.of(eventId, eventDetails)
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