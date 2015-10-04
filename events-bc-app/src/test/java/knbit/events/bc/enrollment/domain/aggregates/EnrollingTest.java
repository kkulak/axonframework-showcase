package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.exceptions.EnrollmentExceptions;
import knbit.events.bc.enrollment.domain.exceptions.EventUnderEnrollmentExceptions;
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EnrollmentCommands;
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.TermModifyingEvents;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 03.10.15.
 */
public class EnrollingTest {

    private FixtureConfiguration<EventUnderEnrollment> fixture;
    private EventId eventId;
    private EventDetails eventDetails;
    private IdentifiedTerm firstTerm;
    private IdentifiedTerm secondTerm;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventUnderEnrollmentFixtureConfiguration();
        eventId = EventId.of("id");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
        firstTerm = IdentifiedTerm.of(
                TermId.of("id1"),
                TermBuilder.instance().location(Location.of("3.21A")).build());
        secondTerm = IdentifiedTerm.of(
                TermId.of("id1"),
                TermBuilder.instance().location(Location.of("3.21B")).build());

    }

    @Test
    public void shouldNotBeAbleToEnrollForNotExistingTerm() throws Exception {
        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(firstTerm, secondTerm)
                        )
                )
                .when(
                        EnrollmentCommands.EnrollFor.of(eventId, TermId.of("fakeId"), MemberId.of("id"))
                )
                .expectException(
                        EventUnderEnrollmentExceptions.NoSuchTermException.class
                );
    }

    @Test
    public void shouldNotBeAbleToEnrollTwiceForTheSameTerm() throws Exception {
        final MemberId memberId = MemberId.of("participantId");

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(firstTerm, secondTerm)
                        ),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, firstTerm.termId(), memberId)
                )
                .when(
                        EnrollmentCommands.EnrollFor.of(eventId, firstTerm.termId(), memberId)
                )
                .expectException(
                        EnrollmentExceptions.AlreadyEnrolledForEvent.class
                );
    }

    @Test
    public void shouldNotBeAbleToEnrollIfAlreadyEnrolledForDifferentTerm() throws Exception {
        final MemberId memberId = MemberId.of("participantId");

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(firstTerm, secondTerm)
                        ),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, firstTerm.termId(), memberId)
                )
                .when(
                        EnrollmentCommands.EnrollFor.of(eventId, secondTerm.termId(), memberId)
                )
                .expectException(
                        EnrollmentExceptions.AlreadyEnrolledForEvent.class
                );
    }

    @Test
    public void shouldNotBeAbleToEnrollIfLimitExceeded() throws Exception {
        final MemberId memberId = MemberId.of("participant1");

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(firstTerm, secondTerm)
                        ),

                        TermModifyingEvents.ParticipantLimitSet.of(eventId, firstTerm.termId(), ParticipantsLimit.of(1)),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, firstTerm.termId(), memberId)
                )
                .when(
                        EnrollmentCommands.EnrollFor.of(eventId, firstTerm.termId(), MemberId.of("participant2"))
                )
                .expectException(
                        EnrollmentExceptions.EnrollmentLimitExceeded.class
                );
    }

    @Test
    public void shouldProduceProperEventOnSuccessfulEnrollment() throws Exception {

        final MemberId memberId = MemberId.of("participantId");

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(firstTerm, secondTerm)
                        )
                )
                .when(
                        EnrollmentCommands.EnrollFor.of(eventId, firstTerm.termId(), memberId)
                )
                .expectEvents(
                        EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, firstTerm.termId(), memberId)
                );
    }
}