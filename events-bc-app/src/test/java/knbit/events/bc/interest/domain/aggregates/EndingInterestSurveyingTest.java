package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.builders.SurveyingInterestStartedEventBuilder;
import knbit.events.bc.interest.domain.exceptions.InterestAwareEventAlreadyTransitedException;
import knbit.events.bc.interest.domain.exceptions.SurveyingInterestAlreadyEndedException;
import knbit.events.bc.interest.domain.exceptions.SurveyingInterestNotYetStartedException;
import knbit.events.bc.interest.domain.valueobjects.commands.QuestionnaireCommands;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents;
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
                        InterestAwareEvents.Created.of(eventId, eventDetails),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .when(
                        QuestionnaireCommands.End.of(eventId)
                )
                .expectEvents(
                        SurveyEvents.Ended.of(eventId)
                );

    }

    @Test
    public void shouldThrowAnExceptionIfSurveyingNotYetInProgress() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        QuestionnaireCommands.End.of(eventId)
                )
                .expectException(SurveyingInterestNotYetStartedException.class);
    }

    @Test
    public void shouldThrowAnExceptionTryingToEndEndedSurvey() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),
                        SurveyEvents.Ended.of(eventId)
                )
                .when(
                        QuestionnaireCommands.End.of(eventId)
                )
                .expectException(SurveyingInterestAlreadyEndedException.class);
    }

    @Test
    public void shouldThrowAnExceptionTryingToEndEventTransitedToUnderChoosingTermEvent() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),

                        InterestAwareEvents.TransitedToUnderChoosingTerm.of(
                                eventId, eventDetails
                        )
                )
                .when(
                        QuestionnaireCommands.End.of(eventId)
                )
                .expectException(InterestAwareEventAlreadyTransitedException.class);
    }
}
