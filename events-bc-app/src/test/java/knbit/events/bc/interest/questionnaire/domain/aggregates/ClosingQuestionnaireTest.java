package knbit.events.bc.interest.questionnaire.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.interest.questionnaire.domain.builders.QuestionnaireCreatedEventBuilder;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.commands.CloseQuestionnaireCommand;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.events.QuestionnaireClosedEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class ClosingQuestionnaireTest {

    private FixtureConfiguration<Questionnaire> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.questionnaireFixtureConfiguration();
    }

    @Test
    public void shouldCreateQuestionnaireClosedEventOnCorrespondingCommand() throws Exception {

        final QuestionnaireId questionnaireId = QuestionnaireId.of("questionId");

        fixture
                .given(
                        QuestionnaireCreatedEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .build()
                )
                .when(
                        new CloseQuestionnaireCommand(questionnaireId)
                )
                .expectEvents(
                        new QuestionnaireClosedEvent(questionnaireId)
                );

    }

    @Test
    public void shouldThrowAnExceptionTryingToCloseAlreadyClosedQuestionnaire() throws Exception {

        final QuestionnaireId questionnaireId = QuestionnaireId.of("questionId");

        fixture
                .given(
                        QuestionnaireCreatedEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .build(),

                        new QuestionnaireClosedEvent(questionnaireId)
                )
                .when(
                        new CloseQuestionnaireCommand(questionnaireId)
                )
                .expectException(IllegalStateException.class);
    }
}
