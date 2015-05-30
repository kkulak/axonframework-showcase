package knbit.events.bc.interest.survey.domain.aggreagates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.backlogevent.domain.valueobjects.EventId;
import knbit.events.bc.interest.survey.domain.builders.SurveyCreatedEventBuilder;
import knbit.events.bc.interest.survey.domain.builders.SurveyVotedDownEventBuilder;
import knbit.events.bc.interest.survey.domain.builders.SurveyVotedUpEventBuilder;
import knbit.events.bc.interest.survey.domain.builders.VoteUpCommandBuilder;
import knbit.events.bc.interest.survey.domain.exceptions.CannotVoteOnClosedSurveyException;
import knbit.events.bc.interest.survey.domain.valueobjects.events.InterestThresholdReachedEvent;
import knbit.events.bc.interest.survey.domain.exceptions.SurveyAlreadyVotedException;
import knbit.events.bc.interest.survey.domain.policies.WithFixedThresholdPolicy;
import knbit.events.bc.interest.survey.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.survey.domain.valueobjects.events.SurveyClosedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class VotingUpTest {

    private FixtureConfiguration<Survey> fixture;
    private SurveyId surveyId;
    private EventId eventId;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.surveyFixtureConfiguration();
        surveyId = SurveyId.of("surveyId");
        eventId = EventId.of("eventId");
    }

    @Test
    public void shouldProduceSurveyVotedUpEventGivenVoteUpCommand() throws Exception {

        final Attendee attendee = Attendee.of("firstname", "lastname");

        fixture
                .given(
                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .build()
                )
                .when(
                        VoteUpCommandBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(attendee)
                                .build()
                )
                .expectEvents(
                        SurveyVotedUpEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(attendee)
                                .build()
                );

    }

    @Test
    public void shouldNotBeAbleToVoteUpTwice() throws Exception {

        final Attendee attendee = Attendee.of("firstname", "lastname");

        fixture
                .given(
                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .build(),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(attendee)
                                .build()
                )
                .when(
                        VoteUpCommandBuilder
                                .instance()
                                .surveyId(surveyId)
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
                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .build(),

                        SurveyVotedDownEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(attendee)
                                .build()
                )
                .when(
                        VoteUpCommandBuilder
                                .instance()
                                .surveyId(surveyId)
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
                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .thresholdPolicy(
                                        new WithFixedThresholdPolicy(interestThreshold)
                                )
                                .build(),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(
                                        Attendee.of("firstname1", "lastname1")
                                )
                                .build(),

                        SurveyVotedDownEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(
                                        Attendee.of("firstname2", "lastname2")
                                )
                                .build(),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(
                                        Attendee.of("firstname3", "lastname3")
                                )
                                .build()


                )
                .when(
                        VoteUpCommandBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(
                                        Attendee.of("firstname4", "firstname4")

                                )
                                .build()
                )
                .expectEvents(
                        new InterestThresholdReachedEvent(surveyId),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(
                                        Attendee.of("firstname4", "firstname4")
                                )
                                .build()
                );

    }

    @Test
    public void shouldNotThrowInterestThresholdReachedEventTwice() throws Exception {

        fixture
                .given(
                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .thresholdPolicy(
                                        new WithFixedThresholdPolicy(1)
                                )
                                .build(),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(
                                        Attendee.of("firstname1", "lastname1")
                                )
                                .build(),

                        new InterestThresholdReachedEvent(surveyId)
                )
                .when(
                        VoteUpCommandBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(
                                        Attendee.of("firstname2", "firstname2")

                                )
                                .build()
                )
                .expectEvents(
                        SurveyVotedUpEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(
                                        Attendee.of("firstname2", "firstname2")
                                )
                                .build()
                );

    }

    @Test
    public void shouldNotBeAbleToVoteOnClosedSurvey() throws Exception {

        fixture
                .given(
                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .build(),

                        new SurveyClosedEvent(surveyId)
                )
                .when(
                        VoteUpCommandBuilder
                                .instance()
                                .surveyId(surveyId)
                                .build()
                )
                .expectException(CannotVoteOnClosedSurveyException.class);
    }
}