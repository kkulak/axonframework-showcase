package knbit.events.bc.interest.common.domain.sagas;

import knbit.events.bc.common.domain.valueobjects.EventId;
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

/*        fixture
                .whenAggregate(eventId)
                .publishes(
                        OneOffEventCreatedBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .expectActiveSagas(1);*/
    }

    @Test
    public void shouldScheduleSurveyingTimeExceededEventWithProperDateGivenDateAwareEvent() throws Exception {

        final EventId eventId = EventId.of("eventId");
        final DateTime surveyEndDate = DateTime.now().plusMonths(2);

/*        fixture
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
                );*/

    }

    @Test
    public void shouldEndWhenSurveyingHasBeenStopped() throws Exception {

        final EventId eventId = EventId.of("eventId");

/*        fixture
                .givenAggregate(eventId)
                .published(
                        OneOffEventCreatedBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .whenPublishingA(
                        new SurveyingInterestStoppedEvent(eventId)
                )
                .expectActiveSagas(0);*/

    }

    @Test
    public void shouldEndWhenTimeoutOccurred() throws Exception {

        final EventId eventId = EventId.of("eventId");
        final DateTime surveyingEndDate = DateTime.now();
/*
        fixture
                .givenAggregate(eventId)
                .published(
                        OneOffEventCreatedBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyWithEndingDateCreatedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .endingSurveyDate(surveyingEndDate)
                                .build()
                )
                .whenPublishingA(
                        SurveyingTimeExceededEventBuilder
                                .instance()
                                .eventId(eventId)
                                .surveyingEndTime(surveyingEndDate)
                                .build()
                )
                .expectActiveSagas(0);*/

    }
}