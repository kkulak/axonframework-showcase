package knbit.events.bc.interest.common.domain.sagas;

import knbit.events.bc.event.domain.builders.OneOffEventCreatedBuilder;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.common.domain.valueobjects.events.SurveyingInterestStoppedEvent;
import knbit.events.bc.interest.common.domain.valueobjects.events.SurveyingTimeExceededEvent;
import knbit.events.bc.interest.survey.domain.builders.SurveyCreatedEventBuilder;
import knbit.events.bc.interest.survey.domain.builders.SurveyWithEndingDateCreatedEventBuilder;
import knbit.events.bc.interest.survey.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.valueobjects.commands.CloseSurveyCommand;
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
    public void shouldCloseSurveyAndQuestionnaireOnSurveyingInterestStoppedEvent() throws Exception {

        final SurveyId surveyId = SurveyId.of("surveyId");

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
                                .build()
                )
                .whenPublishingA(
                        new SurveyingInterestStoppedEvent(eventId)
                )
                .expectDispatchedCommandsEqualTo(
                        new CloseSurveyCommand(surveyId)
                );
        // todo questionnaire

    }

    @Test
    public void shouldCloseSurveyAndQuestionnaireOnTimeout() throws Exception {

        final SurveyId surveyId = SurveyId.of("surveyId");
        final DateTime surveyingEndDate = DateTime.now().plusDays(15);

        fixture
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
                );
    }
}
