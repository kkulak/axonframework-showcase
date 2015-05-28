package knbit.events.bc.interest.common.domain.sagas;

import knbit.events.bc.event.domain.builders.OneOffEventCreatedBuilder;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.common.builders.SurveyingTimeExceededEventBuilder;
import knbit.events.bc.interest.survey.domain.builders.SurveyWithEndingDateCreatedEventBuilder;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class InterestSagaTest {

    private AnnotatedSagaTestFixture fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AnnotatedSagaTestFixture(InterestSaga.class);

    }

    @Test
    public void shouldInitializeOnEventCreation() throws Exception {

        final EventId eventId = EventId.of("eventId");

        fixture
                .whenAggregate(eventId)
                .publishes(
                        OneOffEventCreatedBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .expectActiveSagas(1);
    }

    @Test
    public void shouldScheduleSurveyingTimeExceededEventWithProperDateGivenDateAwareEvent() throws Exception {

        final EventId eventId = EventId.of("eventId");
        final DateTime surveyEndDate = DateTime.now().plusMonths(2);

        fixture
                .givenAggregate(eventId)
                .published(
                        OneOffEventCreatedBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .whenPublishingA(
                        SurveyWithEndingDateCreatedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .endingSurveyDate(surveyEndDate)
                                .build()
                )
                .expectScheduledEvent(
                        surveyEndDate,
                        SurveyingTimeExceededEventBuilder
                                .instance()
                                .eventId(eventId)
                                .surveyingEndTime(surveyEndDate)
                                .build()
                );

    }
}