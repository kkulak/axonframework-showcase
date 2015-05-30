package knbit.events.bc.interest.survey.domain.aggreagates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.aggregates.InterestAwareEvent;
import knbit.events.bc.interest.survey.domain.builders.CreateSurveyCommandBuilder;
import knbit.events.bc.interest.survey.domain.builders.SurveyCreatedEventBuilder;
import knbit.events.bc.interest.survey.domain.builders.SurveyWithEndingDateCreatedEventBuilder;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */
public class CreatingInterestAwareEventTest {

    private FixtureConfiguration<InterestAwareEvent> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.interestAwareEventFixtureConfiguration();
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