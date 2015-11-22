package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.interest.builders.*;
import knbit.events.bc.interest.domain.exceptions.*;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Created by novy on 28.05.15.
 */
public class VotingDownTest {

    private FixtureConfiguration<InterestAwareEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.interestAwareEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
    }

    @Test
    public void shouldProduceSurveyVotedDownEventGivenVoteDownCommand() throws Exception {

        final Attendee attendee = Attendee.of(new MemberId());

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        SurveyingStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()

                )
                .when(
                        VoteDownCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendee)
                                .build()
                )
                .expectEvents(
                        SurveyVotedDownEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendee)
                                .build()
                );


    }

    @Test
    public void shouldNotBeAbleToVoteDownTwice() throws Exception {

        final Attendee attendee = Attendee.of(new MemberId());

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        SurveyingStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyVotedDownEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendee)
                                .build()
                )
                .when(
                        VoteDownCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendee)
                                .build()
                )
                .expectException(SurveyAlreadyVotedException.class);
    }

    @Test
    public void shouldNotBeAbleToVoteUpAfterVotingUp() throws Exception {

        final Attendee attendee = Attendee.of(new MemberId());

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        SurveyingStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendee)
                                .build()
                )
                .when(
                        VoteDownCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendee)
                                .build()
                )
                .expectException(SurveyAlreadyVotedException.class);

    }

    @Test
    public void shouldNotBeAbleToVoteOnClosedSurvey() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        SurveyingStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyEvents.Ended.of(eventId)
                )
                .when(
                        VoteDownCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .expectException(SurveyingInterestAlreadyEndedException.class);
    }

    @Test
    public void shouldNotBeAbleToVoteOnCancelledEvent() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        SurveyingStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        InterestAwareEvents.CancelledDuringOrAfterSurveying.of(
                                eventId,
                                Collections.emptyList()
                        )
                )
                .when(
                        VoteDownCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .expectException(InterestAwareEventAlreadyCancelledException.class);
    }

    @Test
    public void shouldNotBeAbleToVoteIfSurveyingNotStarted() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        )
                )
                .when(
                        VoteDownCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .expectException(SurveyingInterestNotYetStartedException.class);
    }

    @Test
    public void shouldNotBeAbleToVoteIfSurveyTransitedToUnderChoosingTermEvent() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),

                        InterestAwareEvents.TransitedToUnderChoosingTerm.of(
                                eventId, eventDetails
                        )
                )
                .when(
                        VoteDownCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .expectException(InterestAwareEventAlreadyTransitedException.class);
    }
}
