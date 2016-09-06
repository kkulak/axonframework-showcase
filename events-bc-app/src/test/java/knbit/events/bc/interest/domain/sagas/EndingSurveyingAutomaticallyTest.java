package knbit.events.bc.interest.domain.sagas;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.builders.SurveyingInterestWithEndingDateStartedEventBuilder;
import knbit.events.bc.interest.builders.SurveyingTimeExceededEventBuilder;
import knbit.events.bc.interest.domain.valueobjects.commands.QuestionnaireCommands;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class EndingSurveyingAutomaticallyTest {

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
    public void shouldDispatchEndInterestSurveyingCommandOnTimeout() throws Exception {

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
                .expectDispatchedCommandsEqualTo(
                        QuestionnaireCommands.End.of(eventId)
                );
    }
}
