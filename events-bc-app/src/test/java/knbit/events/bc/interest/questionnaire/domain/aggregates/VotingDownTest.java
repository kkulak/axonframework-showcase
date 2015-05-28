package knbit.events.bc.interest.questionnaire.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.interest.questionnaire.domain.builders.QuestionnaireVotedDownEventBuilder;
import knbit.events.bc.interest.questionnaire.domain.builders.VoteQuestionnaireDownCommandBuilder;
import knbit.events.bc.interest.questionnaire.domain.builders.QuestionnaireCreatedEventBuilder;
import knbit.events.bc.interest.questionnaire.domain.exceptions.QuestionnaireAlreadyVotedException;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 26.05.15.
 */

public class VotingDownTest {

    private FixtureConfiguration<Questionnaire> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.questionnaireFixtureConfiguration();
    }

    @Test
    public void shouldProduceQuestionnaireVotedDownEventWhenVoteQuestionnaireDownCommandInvoked() throws Exception {

        final QuestionnaireId questionnaireId = QuestionnaireId.of("questionId");
        final Attendee attendee = Attendee.of("firstname", "lastname");

        fixture
                .given(
                        QuestionnaireCreatedEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .build()
                )
                .when(
                        VoteQuestionnaireDownCommandBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .attendee(attendee)
                                .build()
                )
                .expectEvents(
                        QuestionnaireVotedDownEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .attendee(attendee)
                                .build()
                );
    }

    @Test
    public void shouldNotBeAbleToVoteUpMoreThanOnce() throws Exception {

        final QuestionnaireId questionnaireId = QuestionnaireId.of("questionId");
        final Attendee attendee = Attendee.of("firstname", "lastname");

        fixture
                .given(
                        QuestionnaireCreatedEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .build(),

                        QuestionnaireVotedDownEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .attendee(attendee)
                                .build()
                )
                .when(
                        VoteQuestionnaireDownCommandBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .attendee(attendee)
                                .build()
                )
                .expectException(QuestionnaireAlreadyVotedException.class);

    }
}
