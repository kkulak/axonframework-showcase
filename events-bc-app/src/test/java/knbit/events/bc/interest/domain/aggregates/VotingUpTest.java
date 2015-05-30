package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.*;
import knbit.events.bc.interest.domain.exceptions.SurveyAlreadyVotedException;
import knbit.events.bc.interest.domain.exceptions.SurveyingInterestAlreadyEndedException;
import knbit.events.bc.interest.domain.exceptions.SurveyingInterestNotYetStartedException;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated;
import knbit.events.bc.interest.domain.valueobjects.events.InterestThresholdReachedEvent;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyingInterestEndedEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.survey.domain.policies.WithFixedThresholdPolicy;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class VotingUpTest {

    private FixtureConfiguration<InterestAwareEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.interestAwareEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
    }

    @Test
    public void shouldProduceSurveyVotedUpEventGivenVoteUpCommand() throws Exception {

        final Attendee attendee = Attendee.of("firstname", "lastname");

        fixture
                .given(
                        InterestAwareEventCreated.of(
                                eventId, eventDetails
                        ),

                        SurveyingStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()

                )
                .when(
                        VoteUpCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendee)
                                .build()
                )
                .expectEvents(
                        SurveyVotedUpEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendee)
                                .build()

                );


    }

    @Test
    public void shouldNotBeAbleToVoteUpTwice() throws Exception {

        final Attendee attendee = Attendee.of("firstname", "lastname");

        fixture
                .given(
                        InterestAwareEventCreated.of(
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
                        VoteUpCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendee)
                                .build()
                )
                .expectException(SurveyAlreadyVotedException.class);

    }

    @Test
    public void shouldNotBeAbleToVoteUpAfterVotingDown() throws Exception {

        final Attendee attendee = Attendee.of("firstname", "lastname");

        fixture
                .given(
                        InterestAwareEventCreated.of(
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
                        VoteUpCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendee)
                                .build()
                )
                .expectException(SurveyAlreadyVotedException.class);

    }

    @Test
    public void shouldProduceInterestThresholdReachedIfThresholdSet() throws Exception {

        final int interestThreshold = 3;

        fixture
                .given(
                        InterestAwareEventCreated.of(
                                eventId, eventDetails
                        ),

                        SurveyingStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .interestPolicy(new WithFixedThresholdPolicy(interestThreshold))
                                .build(),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(
                                        Attendee.of("firstname1", "lastname1")
                                )
                                .build(),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(
                                        Attendee.of("firstname2", "lastname2")
                                )
                                .build()
                )
                .when(
                        VoteUpCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(
                                        Attendee.of("firstname3", "lastname3")
                                )
                                .build()
                )
                .expectEvents(
                        InterestThresholdReachedEvent.of(eventId),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(
                                        Attendee.of("firstname3", "lastname3")
                                )
                                .build()
                );

    }

    @Test
    public void shouldNotThrowInterestThresholdReachedEventTwice() throws Exception {

        fixture
                .given(
                        InterestAwareEventCreated.of(
                                eventId, eventDetails
                        ),

                        SurveyingStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .interestPolicy(new WithFixedThresholdPolicy(1))
                                .build(),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(
                                        Attendee.of("firstname1", "lastname1")
                                )
                                .build(),

                        InterestThresholdReachedEvent.of(eventId)
                )
                .when(
                        VoteUpCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(
                                        Attendee.of("firstname2", "lastname2")

                                )
                                .build()
                )
                .expectEvents(
                        SurveyVotedUpEventBuilder
                                .instance()
                                .attendee(
                                        Attendee.of("firstname2", "lastname2")
                                )
                                .build()
                );

    }


    @Test
    public void shouldNotBeAbleToVoteOnClosedSurvey() throws Exception {

        fixture
                .given(
                        InterestAwareEventCreated.of(
                                eventId, eventDetails
                        ),

                        SurveyingStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyingInterestEndedEvent.of(eventId)
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
    public void shouldNotBeAbleToVoteIfSurveyingNotStarted() throws Exception {

        fixture
                .given(
                        InterestAwareEventCreated.of(
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
}