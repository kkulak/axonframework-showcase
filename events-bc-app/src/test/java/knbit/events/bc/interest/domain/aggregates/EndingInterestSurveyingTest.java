package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.builders.SurveyingInterestStartedEventBuilder;
import knbit.events.bc.interest.domain.valueobjects.commands.EndSurveyingInterestCommand;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyingInterestEndedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class EndingInterestSurveyingTest {

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
    public void shouldCauseInterestSurveyingEndedEventOnCorrespondingCommand() throws Exception {

        fixture
                .given(
                        InterestAwareEventCreated.of(eventId, eventDetails),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .when(
                        EndSurveyingInterestCommand.of(eventId)
                )
                .expectEvents(
                        SurveyingInterestEndedEvent.of(eventId)
                );

    }

    @Test
    public void shouldThrowAnExceptionIfSurveyingNotYetInProgress() throws Exception {

        fixture
                .given(
                        InterestAwareEventCreated.of(eventId, eventDetails)
                )
                .when(
                        EndSurveyingInterestCommand.of(eventId)
                )
                .expectException(IllegalStateException.class);
    }

    @Test
    public void shouldThrowAnExceptionTryingToEndEndedSurvey() throws Exception {


        fixture
                .given(
                        InterestAwareEventCreated.of(eventId, eventDetails),
                        SurveyingInterestEndedEvent.of(eventId)
                )
                .when(
                        EndSurveyingInterestCommand.of(eventId)
                )
                .expectException(IllegalStateException.class);
    }
}
