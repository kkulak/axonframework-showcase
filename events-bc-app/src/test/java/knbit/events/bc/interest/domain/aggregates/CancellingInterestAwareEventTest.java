package knbit.events.bc.interest.domain.aggregates;

import com.google.common.collect.ImmutableList;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.builders.SurveyingStartedEventBuilder;
import knbit.events.bc.interest.domain.exceptions.InterestAwareEventAlreadyCancelledException;
import knbit.events.bc.interest.domain.exceptions.InterestAwareEventAlreadyTransitedException;
import knbit.events.bc.interest.domain.valueobjects.commands.InterestAwareEventCommands;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyStartingEvents;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

/**
 * Created by novy on 21.11.15.
 */

@RunWith(JUnitParamsRunner.class)
public class CancellingInterestAwareEventTest {

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
    public void shouldNotBeAbleToCancelAfterTransition() throws Exception {
        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),
                        InterestAwareEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)
                )
                .when(
                        InterestAwareEventCommands.Cancel.of(eventId)
                )
                .expectException(
                        InterestAwareEventAlreadyTransitedException.class
                );
    }

    @Test
    @Parameters(method = "cancellationEvents")
    public void shouldNotBeAbleToCancelTwice(InterestAwareEvents.InterestAwareEventCancelled cancellationEvent) throws Exception {
        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),
                        cancellationEvent
                )
                .when(
                        InterestAwareEventCommands.Cancel.of(eventId)
                )
                .expectException(
                        InterestAwareEventAlreadyCancelledException.class
                );
    }

    @Test
    public void shouldCreateProperEventIfCancelledBeforeSurveyStarted() throws Exception {
        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        InterestAwareEventCommands.Cancel.of(eventId)
                )
                .expectEvents(
                        InterestAwareEvents.CancelledBeforeSurveyStarted.of(eventId)
                );
    }

    @Test
    public void shouldIncludeVotersIfCancelledWhileSurveying() throws Exception {
        final SurveyStartingEvents.Started surveyingStarted = SurveyingStartedEventBuilder.instance()
                .eventId(eventId)
                .eventDetails(eventDetails)
                .build();

        final Attendee attendeeWhoVotedUp = Attendee.of(MemberId.of("positiveVoter"));
        final Attendee attendeeWhoVotedDown = Attendee.of(MemberId.of("negativeVoter"));

        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),
                        surveyingStarted,
                        SurveyEvents.VotedUp.of(eventId, attendeeWhoVotedUp),
                        SurveyEvents.VotedDown.of(eventId, attendeeWhoVotedDown)
                )
                .when(
                        InterestAwareEventCommands.Cancel.of(eventId)
                )
                .expectEvents(
                        InterestAwareEvents.CancelledDuringOrAfterSurveying.of(
                                eventId, ImmutableList.of(attendeeWhoVotedUp, attendeeWhoVotedDown)
                        )
                );
    }

    @Test
    public void shouldIncludeVotersIfCancelledAfterSurveyEnds() throws Exception {
        final SurveyStartingEvents.Started surveyingStarted = SurveyingStartedEventBuilder.instance()
                .eventId(eventId)
                .eventDetails(eventDetails)
                .build();

        final Attendee attendeeWhoVotedUp = Attendee.of(MemberId.of("positiveVoter"));
        final Attendee attendeeWhoVotedDown = Attendee.of(MemberId.of("negativeVoter"));

        fixture
                .given(
                        InterestAwareEvents.Created.of(eventId, eventDetails),
                        surveyingStarted,
                        SurveyEvents.VotedUp.of(eventId, attendeeWhoVotedUp),
                        SurveyEvents.VotedDown.of(eventId, attendeeWhoVotedDown),
                        SurveyEvents.Ended.of(eventId)
                )
                .when(
                        InterestAwareEventCommands.Cancel.of(eventId)
                )
                .expectEvents(
                        InterestAwareEvents.CancelledDuringOrAfterSurveying.of(
                                eventId, ImmutableList.of(attendeeWhoVotedUp, attendeeWhoVotedDown)
                        )
                );
    }

    private Object[] cancellationEvents() {
        return new Object[]{
                new Object[]{InterestAwareEvents.CancelledBeforeSurveyStarted.of(eventId)},
                new Object[]{InterestAwareEvents.CancelledDuringOrAfterSurveying.of(eventId, Collections.emptyList())}
        };
    }
}
