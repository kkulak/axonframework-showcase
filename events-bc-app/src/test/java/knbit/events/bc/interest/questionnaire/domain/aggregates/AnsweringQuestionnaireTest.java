package knbit.events.bc.interest.questionnaire.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.interest.questionnaire.domain.builders.AnswerQuestionnaireCommandBuilder;
import knbit.events.bc.interest.questionnaire.domain.builders.QuestionnaireAnsweredEventBuilder;
import knbit.events.bc.interest.questionnaire.domain.builders.QuestionnaireCreatedEventBuilder;
import knbit.events.bc.interest.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.interest.questionnaire.domain.exceptions.CannotAnswerClosedQuestionnaireException;
import knbit.events.bc.interest.questionnaire.domain.exceptions.QuestionnaireAlreadyAnsweredException;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.events.QuestionnaireClosedEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.DomainAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.IdentifiedQuestionData;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.MultipleChoiceAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.TextAnswer;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Created by novy on 26.05.15.
 */
public class AnsweringQuestionnaireTest {

    private FixtureConfiguration<Questionnaire> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.questionnaireFixtureConfiguration();
    }

    @Test
    public void shouldProduceQuestionnaireAnsweredEventWhenAnswerQuestionnaireCommandInvoked() throws Exception {

        final QuestionnaireId questionnaireId = QuestionnaireId.of("questionnaireId");
        final Attendee attendee = Attendee.of("firstname", "lastname");

        final IdentifiedQuestionData textQuestion = IdentifiedQuestionData.of(
                QuestionId.of("id1"),
                QuestionData.of("t1", "d1", QuestionType.TEXT, Collections.emptyList())
        );

        final IdentifiedQuestionData multiChoiceQuestion = IdentifiedQuestionData.of(
                QuestionId.of("id2"),
                QuestionData.of("t2", "d2", QuestionType.MULTIPLE_CHOICE, ImmutableList.of("opt1", "opt2", "opt3"))
        );

        fixture
                .given(
                        QuestionnaireCreatedEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .question(textQuestion)
                                .question(multiChoiceQuestion)
                                .build()
                )
                .when(
                        AnswerQuestionnaireCommandBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .attendee(attendee)
                                .answer(
                                        new TextAnswer(QuestionId.of("id1"), "answer")
                                )
                                .answer(
                                        new MultipleChoiceAnswer(
                                                QuestionId.of("id2"),
                                                ImmutableList.of("opt2", "opt3")
                                        )
                                )
                                .build()
                )
                .expectEvents(
                        QuestionnaireAnsweredEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .attendee(attendee)
                                .answeredQuestion(
                                        AnsweredQuestion.of(
                                                textQuestion,
                                                ImmutableList.of(DomainAnswer.of("answer"))
                                        )
                                )
                                .answeredQuestion(
                                        AnsweredQuestion.of(
                                                multiChoiceQuestion,
                                                ImmutableList.of(
                                                        DomainAnswer.of("opt2"),
                                                        DomainAnswer.of("opt3")
                                                )
                                        )
                                )
                                .build()
                );


    }

    @Test
    public void shouldNotBeAbleToAnswerMoreThanOnce() throws Exception {

        final QuestionnaireId questionnaireId = QuestionnaireId.of("questionnaireId");
        final Attendee attendee = Attendee.of("firstname", "lastname");

        final IdentifiedQuestionData textQuestion = IdentifiedQuestionData.of(
                QuestionId.of("id1"),
                QuestionData.of("t1", "d1", QuestionType.TEXT, Collections.emptyList())
        );

        fixture
                .given(
                        QuestionnaireCreatedEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .question(textQuestion)
                                .build(),

                        QuestionnaireAnsweredEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .attendee(attendee)
                                .answeredQuestion(
                                        AnsweredQuestion.of(
                                                textQuestion,
                                                ImmutableList.of(DomainAnswer.of("answer"))
                                        )
                                )
                                .build()
                )
                .when(
                        AnswerQuestionnaireCommandBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .attendee(attendee)
                                .answer(
                                        new TextAnswer(QuestionId.of("id1"), "another answer")
                                )
                                .build()
                )
                .expectException(QuestionnaireAlreadyAnsweredException.class);

    }

    @Test
    public void shouldNotBeAbleToAnswerClosedQuestionnaire() throws Exception {

        final QuestionnaireId questionnaireId = QuestionnaireId.of("questionnaireId");
        final Attendee attendee = Attendee.of("firstname", "lastname");

        final IdentifiedQuestionData textQuestion = IdentifiedQuestionData.of(
                QuestionId.of("id1"),
                QuestionData.of("t1", "d1", QuestionType.TEXT, Collections.emptyList())
        );

        fixture
                .given(
                        QuestionnaireCreatedEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .question(textQuestion)
                                .build(),

                        new QuestionnaireClosedEvent(questionnaireId)
                )
                .when(
                        AnswerQuestionnaireCommandBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .attendee(attendee)
                                .answer(
                                        new TextAnswer(QuestionId.of("id1"), "another answer")
                                )
                                .build()
                )
                .expectException(CannotAnswerClosedQuestionnaireException.class);

    }
}
