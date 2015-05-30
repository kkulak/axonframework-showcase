package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.builders.StartSurveyingInterestCommandBuilder;
import knbit.events.bc.interest.builders.SurveyingInterestStartedEventBuilder;
import knbit.events.bc.interest.builders.SurveyingInterestWithEndingDateStartedEventBuilder;
import knbit.events.bc.interest.domain.valueobjects.commands.CreateInterestAwareEventCommand;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */
public class StartingInterestSurveyingTest {

    private FixtureConfiguration<InterestAwareEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.interestAwareEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
    }

    @Test
    public void givenCommandWithoutEndingDateShouldCauseSurveyingInterestStartedEvent() throws Exception {

        fixture
                .given(
                        CreateInterestAwareEventCommand.of(eventId, eventDetails)
                )
                .when(
                        StartSurveyingInterestCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .expectEvents(
                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                );

    }

    @Test
    public void givenCommandWithEndingDateShouldCauseSurveyingInterestWithEndingDateStartedCreatedEvent()
            throws Exception {

        final DateTime now = DateTime.now();

        fixture
                .given(
                        CreateInterestAwareEventCommand.of(eventId, eventDetails)
                )
                .when(
                        StartSurveyingInterestCommandBuilder
                                .instance()
                                .eventId(eventId)
                                .endingSurveyDate(Optional.of(now))
                                .build()
                )
                .expectEvents(
                        SurveyingInterestWithEndingDateStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .endingSurveyDate(now)
                                .build()
                );
    }
}