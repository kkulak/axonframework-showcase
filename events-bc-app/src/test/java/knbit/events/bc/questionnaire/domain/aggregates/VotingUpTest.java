package knbit.events.bc.questionnaire.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.questionnaire.domain.builders.QuestionnaireCreatedEventBuilder;
import knbit.events.bc.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.questionnaire.domain.valueobjects.question.IdentifiedQuestionData;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionData;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Created by novy on 26.05.15.
 */
public class VotingUpTest {

    private FixtureConfiguration<Questionnaire> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.questionnaireFixtureConfiguration();
    }

    @Test
    public void shouldProduceEventVotedUpWhenVoteUpCommandInvoked() throws Exception {

//        final QuestionnaireId questionId = QuestionnaireId.of("questionId");
//        final Attendee attendee = Attendee.of("firstname", "lastname");
//
//        final IdentifiedQuestionData textQuestion = IdentifiedQuestionData.of(
//                QuestionId.of("id1"),
//                QuestionData.of("t1", "d1", QuestionType.TEXT, Collections.emptyList())
//        );
//
//        final IdentifiedQuestionData multiChoiceQuestion = IdentifiedQuestionData.of(
//                QuestionId.of("id2"),
//                QuestionData.of("t2", "d2", QuestionType.TEXT, ImmutableList.of("opt1", "opt2"))
//        );
//
//
//        fixture
//                .given(
//                        QuestionnaireCreatedEventBuilder
//                                .instance()
//                                .questionId(questionId)
//                                .question(textQuestion)
//                                .question(multiChoiceQuestion)
//                                .build()
//
//                )
//                .when(
//
//                )


    }

    @Test
    public void shouldNotBeAbleToVoteDownMoreThanOnce() throws Exception {


    }
}
