package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.exceptions.EnrollmentExceptions;
import knbit.events.bc.enrollment.domain.exceptions.EventUnderEnrollmentExceptions;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTerm;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantId;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EnrollmentCommands;
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 03.10.15.
 */
public class DisenrollingTest {

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
    public void shouldNotBeAbleToDisenrollForNotExistingTerm() throws Exception {
        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(firstTerm, secondTerm)
                        )
                )
                .when(
                        EnrollmentCommands.DissenrollFrom.of(
                                eventId,
                                TermId.of("fakeId"),
                                ParticipantId.of("participantId")
                        )
                )
                .expectException(
                        EventUnderEnrollmentExceptions.NoSuchTermException.class
                );
    }

    @Test
    public void shouldNotBeAbleToDisenrollIfNotEnrolledForGivenTerm() throws Exception {
        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(firstTerm, secondTerm)
                        )
                )
                .when(
                        EnrollmentCommands.DissenrollFrom.of(
                                eventId,
                                firstTerm.termId(),
                                ParticipantId.of("participantId")
                        )
                )
                .expectException(
                        EnrollmentExceptions.NotYetEnrolled.class
                );
    }

    @Test
    public void shouldProduceProperEventOnSuccessfulDisenrollment() throws Exception {

        final ParticipantId participantId = ParticipantId.of("participantId");

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(firstTerm, secondTerm)
                        ),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(
                                eventId,
                                firstTerm.termId(),
                                participantId
                        )
                )
                .when(
                        EnrollmentCommands.DissenrollFrom.of(eventId, firstTerm.termId(), participantId)
                )
                .expectEvents(
                        EnrollmentEvents.ParticipantDisenrolledFromTerm.of(
                                eventId,
                                firstTerm.termId(),
                                participantId
                        )
                );
    }
}
