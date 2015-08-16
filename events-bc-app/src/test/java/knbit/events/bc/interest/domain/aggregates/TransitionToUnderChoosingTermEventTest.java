package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.builders.SurveyingInterestStartedEventBuilder;
import knbit.events.bc.interest.domain.exceptions.InterestAwareEventAlreadyTransitedException;
import knbit.events.bc.interest.domain.exceptions.SurveyingInterestAlreadyInProgressException;
import knbit.events.bc.interest.domain.exceptions.SurveyingInterestNotYetStartedException;
import knbit.events.bc.interest.domain.valueobjects.commands.TransitInterestAwareEventToUnderTermChoosingEventCommand;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventTransitedToUnderChoosingTermEvent;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyingInterestEndedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

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
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
    }

    @Test
    public void shouldNotBeAbleToTransitIfEventSurveyingNotYetStarted() throws Exception {
        fixture
                .given(
                        InterestAwareEventCreated.of(eventId, eventDetails)
                )
                .when(
                        TransitInterestAwareEventToUnderTermChoosingEventCommand.of(eventId)
                )
                .expectException(
                        SurveyingInterestNotYetStartedException.class
                );
    }

    @Test
    public void shouldNotBeAbleToTransitIfEventSurveyingIsInProgress() throws Exception {
        fixture
                .given(
                        InterestAwareEventCreated.of(eventId, eventDetails),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .when(
                        TransitInterestAwareEventToUnderTermChoosingEventCommand.of(eventId)
                )
                .expectException(
                        SurveyingInterestAlreadyInProgressException.class
                );
    }

    @Test
    public void shouldTransitToUnderChoosingTermEventGivenCorrespondingCommand() throws Exception {
        fixture
                .given(
                        InterestAwareEventCreated.of(eventId, eventDetails),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyingInterestEndedEvent.of(eventId)
                )
                .when(
                        TransitInterestAwareEventToUnderTermChoosingEventCommand.of(eventId)
                )
                .expectEvents(
                        InterestAwareEventTransitedToUnderChoosingTermEvent.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldNotBeAbleToTransitAlreadyTransitedEvent() throws Exception {
        fixture
                .given(
                        InterestAwareEventCreated.of(eventId, eventDetails),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyingInterestEndedEvent.of(eventId),

                        InterestAwareEventTransitedToUnderChoosingTermEvent.of(eventId, eventDetails)
                )
                .when(
                        TransitInterestAwareEventToUnderTermChoosingEventCommand.of(eventId)
                )
                .expectException(
                        InterestAwareEventAlreadyTransitedException.class
                );
    }
}
