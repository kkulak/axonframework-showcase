package knbit.events.bc.questionnaire.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.questionnaire.domain.builders.CreateQuestionnaireCommandBuilder;
import knbit.events.bc.questionnaire.domain.builders.QuestionnaireCreatedEventBuilder;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

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

        fixture
                .givenNoPriorActivity()
                .when(
                        CreateQuestionnaireCommandBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .eventId(eventId)
                                .build()

                )
                .expectEvents(
                        QuestionnaireCreatedEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .eventId(eventId)
                                .build()
                );
    }
}