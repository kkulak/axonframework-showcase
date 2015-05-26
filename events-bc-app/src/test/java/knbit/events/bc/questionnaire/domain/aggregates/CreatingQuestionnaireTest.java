package knbit.events.bc.questionnaire.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.questionnaire.domain.builders.CreateQuestionnaireCommandBuilder;
import knbit.events.bc.questionnaire.domain.builders.QuestionnaireCreatedEventBuilder;
import knbit.events.bc.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionData;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static knbit.events.bc.questionnaire.domain.aggregates.WithoutIdentifierMatcher.matchesButQuestionId;

/**
 * Created by novy on 26.05.15.
 */
public class CreatingQuestionnaireTest {

    private FixtureConfiguration<Questionnaire> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.questionnaireFixtureConfiguration();
    }

    @Test
    public void shouldCreateQuestionnaireGivenCreateQuestionnaireCommand() throws Exception {

        final QuestionnaireId questionnaireId = QuestionnaireId.of("questionnaireId");
        final EventId eventId = EventId.of("eventId");

        final QuestionData singleChoiceQuestion = QuestionData.of(
                "q1", "d1", QuestionType.SINGLE_CHOICE, ImmutableList.of("opt1", "opt11"));
        final QuestionData multipleChoiceQuestion = QuestionData.of(
                "q1", "q2", QuestionType.MULTIPLE_CHOICE, ImmutableList.of("opt2", "opt22"));
        final QuestionData textQuestion = QuestionData.of(
                "q3", "q3", QuestionType.TEXT, Collections.emptyList());

        fixture
                .givenNoPriorActivity()
                .when(
                        CreateQuestionnaireCommandBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .eventId(eventId)
                                .question(singleChoiceQuestion)
                                .question(multipleChoiceQuestion)
                                .question(textQuestion)
                                .build()

                )
                .expectEventsMatching(
                        matchesButQuestionId(
                                ImmutableList.of(
                                        QuestionnaireCreatedEventBuilder
                                                .instance()
                                                .questionnaireId(questionnaireId)
                                                .eventId(eventId)
                                                .question(QuestionId.of("doesn't matter"), singleChoiceQuestion)
                                                .question(QuestionId.of("doesn't matter"), multipleChoiceQuestion)
                                                .question(QuestionId.of("doesn't matter"), textQuestion)
                                                .build()
                                )
                        )

                );
    }
}