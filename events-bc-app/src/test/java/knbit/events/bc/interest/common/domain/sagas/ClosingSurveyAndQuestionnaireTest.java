package knbit.events.bc.interest.common.domain.sagas;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class ClosingSurveyAndQuestionnaireTest {

    private AnnotatedSagaTestFixture fixture;
    private EventId eventId;

    @Before
    public void setUp() throws Exception {
        fixture = new AnnotatedSagaTestFixture(InterestSaga.class);
        eventId = EventId.of("eventId");
    }

    @Test
    public void shouldCloseOnlySurveyOnSurveyingInterestStoppedEventIfThereIsNoQuestionnaire() throws Exception {

//        final SurveyId surveyId = SurveyId.of("surveyId");

/*        fixture
                .givenAggregate(eventId)
                .published(
                        OneOffEventCreatedBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .build()
                )
                .whenPublishingA(
                        new SurveyingInterestStoppedEvent(eventId)
                )
                .expectDispatchedCommandsEqualTo(
                        new CloseSurveyCommand(surveyId)
                );*/

    }

    @Test
    public void shouldCloseSurveyAndQuestionnaireOnSurveyingInterestStoppedEventIfThereIsQuestionnaire()
            throws Exception {

//        final SurveyId surveyId = SurveyId.of("surveyId");
        final QuestionnaireId questionnaireId = QuestionnaireId.of("questionnaireId");

/*
        fixture
                .givenAggregate(eventId)
                .published(
                        OneOffEventCreatedBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .build(),

                        QuestionnaireCreatedEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .eventId(eventId)
                                .build()
                )
                .whenPublishingA(
                        new SurveyingInterestStoppedEvent(eventId)
                )
                .expectDispatchedCommandsEqualTo(
                        new CloseSurveyCommand(surveyId),
                        new CloseQuestionnaireCommand(questionnaireId)
                );
*/

    }

    @Test
    public void shouldCloseOnlySurveyOnTimeoutIfThereIsNoQuestionnaire() throws Exception {

//        final SurveyId surveyId = SurveyId.of("surveyId");
        final DateTime surveyingEndDate = DateTime.now().plusDays(15);

/*        fixture
                .givenAggregate(eventId)
                .published(
                        OneOffEventCreatedBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyWithEndingDateCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .endingSurveyDate(surveyingEndDate)
                                .build()
                )
                .whenPublishingA(
                        new SurveyingTimeExceededEvent(eventId, surveyingEndDate)
                )
                .expectDispatchedCommandsEqualTo(
                        new CloseSurveyCommand(surveyId)
                );*/
    }

    @Test
    public void shouldCloseSurveyAndQuestionnaireOnTimeoutIfThereIsQuestionnaire() throws Exception {

//        final SurveyId surveyId = SurveyId.of("surveyId");
        final QuestionnaireId questionnaireId = QuestionnaireId.of("questionnaireId");
        final DateTime surveyingEndDate = DateTime.now().plusDays(15);

/*        fixture
                .givenAggregate(eventId)
                .published(
                        OneOffEventCreatedBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyWithEndingDateCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .endingSurveyDate(surveyingEndDate)
                                .build(),

                        QuestionnaireCreatedEventBuilder
                                .instance()
                                .questionnaireId(questionnaireId)
                                .eventId(eventId)
                                .build()
                )
                .whenPublishingA(
                        new SurveyingTimeExceededEvent(eventId, surveyingEndDate)
                )
                .expectDispatchedCommandsEqualTo(
                        new CloseSurveyCommand(surveyId),
                        new CloseQuestionnaireCommand(questionnaireId)
                );*/
    }
}
