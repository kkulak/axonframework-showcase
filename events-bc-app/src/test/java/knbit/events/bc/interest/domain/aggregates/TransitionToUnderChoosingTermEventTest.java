package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.builders.SurveyingInterestStartedEventBuilder;
import knbit.events.bc.interest.domain.exceptions.InterestAwareEventAlreadyCancelledException;
import knbit.events.bc.interest.domain.exceptions.InterestAwareEventAlreadyTransitedException;
import knbit.events.bc.interest.domain.exceptions.SurveyingInterestNotYetStartedException;
import knbit.events.bc.interest.domain.valueobjects.commands.InterestAwareEventCommands;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Created by novy on 16.08.15.
 */
public class TransitionToUnderChoosingTermEventTest {

    private FixtureConfiguration<InterestAwareEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.interestAwareEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
    }

    @Test
    public void shouldNotBeAbleToTransitIfEventSurveyingNotYetStarted() throws Exception {
        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        InterestAwareEventCommands.TransitToUnderTermChoosingEvent.of(eventId)
                )
                .expectException(
                        SurveyingInterestNotYetStartedException.class
                );
    }

    @Test
    public void ifSurveyingIsInProgressItShouldEndItAndThenTransitToUnderChoosingTermEvent() throws Exception {
        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .when(
                        InterestAwareEventCommands.TransitToUnderTermChoosingEvent.of(eventId)
                )
                .expectEvents(
                        SurveyEvents.Ended.of(eventId),

                        InterestAwareEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldTransitToUnderChoosingTermEventGivenCorrespondingCommand() throws Exception {
        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyEvents.Ended.of(eventId)
                )
                .when(
                        InterestAwareEventCommands.TransitToUnderTermChoosingEvent.of(eventId)
                )
                .expectEvents(
                        InterestAwareEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldNotBeAbleToTransitAlreadyTransitedEvent() throws Exception {
        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyEvents.Ended.of(eventId),

                        InterestAwareEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)
                )
                .when(
                        InterestAwareEventCommands.TransitToUnderTermChoosingEvent.of(eventId)
                )
                .expectException(
                        InterestAwareEventAlreadyTransitedException.class
                );
    }

    @Test
    public void shouldNotBeAbleToTransitIfCancelled() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyEvents.Ended.of(eventId),

                        InterestAwareEvents.CancelledDuringOrAfterSurveying.of(
                                eventId,
                                Collections.emptyList()
                        )
                )
                .when(
                        InterestAwareEventCommands.TransitToUnderTermChoosingEvent.of(eventId)
                )
                .expectException(
                        InterestAwareEventAlreadyCancelledException.class
                );
    }
}
