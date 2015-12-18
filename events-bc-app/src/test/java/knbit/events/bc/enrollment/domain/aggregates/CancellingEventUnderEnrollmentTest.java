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

/**
 * Created by novy on 22.11.15.
 */
public class CancellingEventUnderEnrollmentTest {

    private FixtureConfiguration<EventUnderEnrollment> fixture;
    private EventId eventId;
    private EventDetails details;
    private EnrollmentIdentifiedTerm identifiedTerm;
    private IdentifiedTermWithAttendees termWithAttendees;
    private MemberId theOnlyMember;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventUnderEnrollmentFixtureConfiguration();
        eventId = EventId.of("id");
        details = EventDetailsBuilder.defaultEventDetails();
        identifiedTerm = EnrollmentIdentifiedTermBuilder.defaultTerm();
        theOnlyMember = MemberId.of("id");
        termWithAttendees = IdentifiedTermWithAttendees.of(
                identifiedTerm.termId(),
                identifiedTerm.duration(),
                identifiedTerm.participantsLimit(),
                identifiedTerm.location(),
                ImmutableList.of(Lecturer.of("John Doe", "john-doe")),
                ImmutableList.of(Attendee.of(theOnlyMember))
        );
    }

    @Test
    public void shouldNotBeAbleToCancelIfEventTransited() throws Exception {

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, details, ImmutableList.of(identifiedTerm)),
                        EventUnderEnrollmentEvents.TransitedToReady.of(eventId, details, ImmutableList.of(termWithAttendees))
                )
                .when(
                        EventUnderEnrollmentCommands.Cancel.of(eventId)
                )
                .expectException(EventUnderEnrollmentExceptions.AlreadyTransitedToReady.class);
    }

    @Test
    public void shouldNotBeAbleToCancelTwice() throws Exception {

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, details, ImmutableList.of(identifiedTerm)),

                        EventUnderEnrollmentEvents.Cancelled.of(
                                eventId,
                                details,
                                ImmutableList.of(termWithAttendees)
                        )
                )
                .when(
                        EventUnderEnrollmentCommands.Cancel.of(eventId)
                )
                .expectException(EventUnderEnrollmentExceptions.AlreadyCancelled.class);
    }

    @Test
    public void shouldProduceEventWithAttendeesPerTerm() throws Exception {

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, details, ImmutableList.of(identifiedTerm)),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(
                                eventId, identifiedTerm.termId(), theOnlyMember
                        )
                )
                .when(
                        EventUnderEnrollmentCommands.Cancel.of(eventId)
                )
                .expectEvents(
                        EventUnderEnrollmentEvents.Cancelled.of(
                                eventId,
                                details,
                                ImmutableList.of(termWithAttendees)
                        )
                );
    }
}
