package knbit.events.bc.interest.domain.sagas;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.builders.SurveyingInterestWithEndingDateStartedEventBuilder;
import knbit.events.bc.interest.builders.SurveyingStartedEventBuilder;
import knbit.events.bc.interest.builders.SurveyingTimeExceededEventBuilder;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class InterestSagaLifecycleTest {

    private AnnotatedSagaTestFixture fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = new AnnotatedSagaTestFixture(InterestSaga.class);
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
    }

    @Test
    public void shouldNotStartIfEndingDateNotSpecified() throws Exception {

        fixture
                .whenAggregate(eventId)
                .publishes(
                        SurveyingStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .expectActiveSagas(0);
    }

    @Test
    public void shouldStartIfEndingDateSpecified() throws Exception {

        fixture
                .whenAggregate(eventId)
                .publishes(
                        SurveyingInterestWithEndingDateStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .endingSurveyDate(DateTime.now().plusDays(15))
                                .build()
                )
                .expectActiveSagas(1);
    }

    @Test
    public void shouldScheduleSurveyingTimeExceededEventWithProperDateGivenDateAwareEvent() throws Exception {

        final DateTime endingSurveyDate = DateTime.now().plusDays(15);

        fixture
                .whenAggregate(eventId)
                .publishes(
                        SurveyingInterestWithEndingDateStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .endingSurveyDate(endingSurveyDate)
                                .build()
                )
                .expectScheduledEvent(
                        endingSurveyDate,
                        SurveyingTimeExceededEventBuilder
                                .instance()
                                .eventId(eventId)
                                .surveyingEndTime(endingSurveyDate)
                                .build()
                );

    }

    @Test
    public void shouldCancelScheduledEventIfSurveyingEndedManually() throws Exception {

        final DateTime endingSurveyDate = DateTime.now().plusDays(15);

        fixture
                .givenAggregate(eventId)
                .published(
                        SurveyingInterestWithEndingDateStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .endingSurveyDate(endingSurveyDate)
                                .build()
                )
                .whenPublishingA(
                        SurveyEvents.Ended.of(eventId)
                )
                .expectNoScheduledEvents();

    }

    @Test
    public void shouldEndIfSurveyingEndedManually() throws Exception {

        final DateTime endingSurveyDate = DateTime.now().plusDays(15);

        fixture
                .givenAggregate(eventId)
                .published(
                        SurveyingInterestWithEndingDateStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .endingSurveyDate(endingSurveyDate)
                                .build()
                )
                .whenPublishingA(
                        SurveyEvents.Ended.of(eventId)
                )
                .expectActiveSagas(0);

    }

    @Test
    public void shouldEndIfSurveyingEndedBecauseOfTimeout() throws Exception {

        final DateTime endingSurveyDate = DateTime.now().plusDays(15);

        fixture
                .givenAggregate(eventId)
                .published(
                        SurveyingInterestWithEndingDateStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .endingSurveyDate(endingSurveyDate)
                                .build()
                )
                .whenPublishingA(
                        SurveyingTimeExceededEventBuilder
                                .instance()
                                .eventId(eventId)
                                .surveyingEndTime(endingSurveyDate)
                                .build()
                )
                .expectActiveSagas(0);

    }
}