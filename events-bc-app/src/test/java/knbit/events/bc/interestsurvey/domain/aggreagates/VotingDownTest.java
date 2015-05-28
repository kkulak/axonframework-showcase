package knbit.events.bc.interestsurvey.domain.aggreagates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interestsurvey.domain.builders.SurveyCreatedEventBuilder;
import knbit.events.bc.interestsurvey.domain.builders.SurveyVotedDownEventBuilder;
import knbit.events.bc.interestsurvey.domain.builders.SurveyVotedUpEventBuilder;
import knbit.events.bc.interestsurvey.domain.builders.VoteDownCommandBuilder;
import knbit.events.bc.interestsurvey.domain.exceptions.SurveyAlreadyVotedException;
import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;
import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class VotingDownTest {

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
    public void shouldProduceSurveyVotedDownEventGivenVoteDownCommand() throws Exception {

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
                        VoteDownCommandBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(attendee)
                                .build()
                )
                .expectEvents(
                        SurveyVotedDownEventBuilder
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

                        SurveyVotedDownEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(attendee)
                                .build()
                )
                .when(
                        VoteDownCommandBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(attendee)
                                .build()
                )
                .expectException(SurveyAlreadyVotedException.class);

    }

    @Test
    public void shouldNotBeAbleToVoteUpAfterVotingUp() throws Exception {

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
                        VoteDownCommandBuilder
                                .instance()
                                .surveyId(surveyId)
                                .attendee(attendee)
                                .build()
                )
                .expectException(SurveyAlreadyVotedException.class);

    }

}
