package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.exceptions.EventUnderEnrollmentExceptions;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTerm;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.TermModifyingCommands;
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
public class SettingMemberIdLimitTest {

    private FixtureConfiguration<EventUnderEnrollment> fixture;
    private EventId eventId;
    private EventDetails eventDetails;
    private IdentifiedTerm identifiedTerm;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventUnderEnrollmentFixtureConfiguration();
        eventId = EventId.of("id");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
        identifiedTerm = IdentifiedTerm.of(TermId.of("id"), TermBuilder.defaultTerm());
    }

    @Test
    public void shouldNotBeAbleToSetLimitOnNotExistingTerm() throws Exception {
        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm))
                )
                .when(
                        TermModifyingCommands.SetParticipantLimit.of(eventId, TermId.of("fakeId"), 666)
                )
                .expectException(
                        EventUnderEnrollmentExceptions.NoSuchTermException.class
                );
    }

    @Test
    public void shouldNotBeAbleToSetLimitLowerThanCurrentParticipantCount() throws Exception {
        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(identifiedTerm)
                        ),

                        TermModifyingEvents.ParticipantLimitSet.of(
                                eventId,
                                identifiedTerm.termId(),
                                ParticipantsLimit.of(2)
                        ),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(
                                eventId,
                                identifiedTerm.termId(),
                                MemberId.of("participant1")
                        ),

                        EnrollmentEvents.ParticipantEnrolledForTerm.of(
                                eventId,
                                identifiedTerm.termId(),
                                MemberId.of("participant2")
                        )
                )
                .when(
                        TermModifyingCommands.SetParticipantLimit.of(eventId, identifiedTerm.termId(), 1)
                )
                .expectException(
                        EventUnderEnrollmentExceptions.ParticipantLimitTooLow.class
                );

    }

    @Test
    public void shouldNotBeAbleToSetLimitHigherThanRoomCapacity() throws Exception {
        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm))
                )
                .when(
                        TermModifyingCommands.SetParticipantLimit.of(
                                eventId,
                                identifiedTerm.termId(),
                                identifiedTerm.capacity().value() + 1
                        )
                )
                .expectException(
                        EventUnderEnrollmentExceptions.ParticipantLimitTooHigh.class
                );
    }

    @Test
    public void otherwiseItShouldProduceProperDomainEvent() throws Exception {
        final Term term = TermBuilder
                .instance()
                .capacity(Capacity.of(60))
                .build();

        final IdentifiedTerm identifiedTerm = IdentifiedTerm.of(TermId.of("id"), term);

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm))
                )
                .when(
                        TermModifyingCommands.SetParticipantLimit.of(eventId, identifiedTerm.termId(), 30)
                )
                .expectEvents(
                        TermModifyingEvents.ParticipantLimitSet.of(
                                eventId,
                                identifiedTerm.termId(),
                                ParticipantsLimit.of(30))
                );
    }
}
