package knbit.events.bc.interestsurvey.domain.aggreagates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interestsurvey.domain.builders.CreateSurveyCommandBuilder;
import knbit.events.bc.interestsurvey.domain.builders.SurveyCreatedEventBuilder;
import knbit.events.bc.interestsurvey.domain.builders.SurveyWithEndingDateCreatedEventBuilder;
import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */
public class CreatingSurveyTest {

    private FixtureConfiguration<Survey> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.surveyFixtureConfiguration();
    }

    @Test
    public void givenCommandWithoutEndingDateShouldProduceSurveyCreatedEvent() throws Exception {

        final SurveyId surveyId = SurveyId.of("surveyId");
        final EventId eventId = EventId.of("eventId");

        fixture
                .givenNoPriorActivity()
                .when(
                        CreateSurveyCommandBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .endingSurveyDate(Optional.empty())
                                .build()
                )
                .expectEvents(
                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .build()
                );

    }

    @Test
    public void givenCommandWithEndingDateShouldProduceSurveyWithEndingDateCreatedEvent() throws Exception {

        final SurveyId surveyId = SurveyId.of("surveyId");
        final EventId eventId = EventId.of("eventId");
        final DateTime now = DateTime.now();

        fixture
                .givenNoPriorActivity()
                .when(
                        CreateSurveyCommandBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .endingSurveyDate(Optional.of(now))
                                .build()
                )
                .expectEvents(
                        SurveyWithEndingDateCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .eventId(eventId)
                                .endingSurveyDate(now)
                                .build()
                );
    }
}