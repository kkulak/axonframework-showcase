package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.SurveyVotedEventBuilder;
import knbit.events.bc.interest.builders.SurveyingStartedEventBuilder;
import knbit.events.bc.interest.domain.enums.VoteType;
import knbit.events.bc.interest.domain.exceptions.SurveyAlreadyVotedException;
import knbit.events.bc.interest.domain.valueobjects.Vote;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated;
import knbit.events.bc.interest.domain.valueobjects.events.InterestThresholdReachedEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.survey.domain.builders.VoteUpCommandBuilder;
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
        eventDetails = new EventDetails(
                Name.of("name"),
                Description.of("desc"),
                EventType.WORKSHOP,
                EventFrequency.ONE_OFF
        );
    }

    @Test
    public void shouldProduceSurveyVotedEventWithCorrespondingTypeGivenVoteUpCommand() throws Exception {

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
                        SurveyVotedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .vote(
                                        Vote.of(attendee, VoteType.POSITIVE)
                                )
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

                        SurveyVotedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .vote(
                                        Vote.of(attendee, VoteType.POSITIVE)
                                )
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

                        SurveyVotedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .vote(
                                        Vote.of(attendee, VoteType.NEGATIVE)
                                )
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

                        SurveyVotedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .vote(
                                        Vote.of(
                                                Attendee.of("firstname1", "lastname1"),
                                                VoteType.POSITIVE
                                        )
                                )
                                .build(),

                        SurveyVotedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .vote(
                                        Vote.of(
                                                Attendee.of("firstname2", "lastname2"),
                                                VoteType.POSITIVE
                                        )
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

                        SurveyVotedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .vote(
                                        Vote.of(
                                                Attendee.of("firstname3", "lastname3"),
                                                VoteType.POSITIVE
                                        )
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

                        SurveyVotedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .vote(
                                        Vote.of(
                                                Attendee.of("firstname1", "lastname1"),
                                                VoteType.POSITIVE
                                        )
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
                        SurveyVotedEventBuilder
                                .instance()
                                .vote(
                                        Vote.of(
                                                Attendee.of("firstname2", "lastname2"),
                                                VoteType.POSITIVE
                                        )
                                )
                                .build()
                );

    }
//
//    @Test
//    public void shouldNotBeAbleToVoteOnClosedSurvey() throws Exception {
//
//        fixture
//                .given(
//                        SurveyCreatedEventBuilder
//                                .instance()
//                                .build()
//
////                        new SurveyClosedEvent(surveyId)
//                )
//                .when(
//                        VoteUpCommandBuilder
//                                .instance()
//                                .build()
//                )
//                .expectException(CannotVoteOnClosedSurveyException.class);
//    }
}