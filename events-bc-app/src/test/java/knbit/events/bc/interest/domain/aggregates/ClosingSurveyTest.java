package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.aggregates.InterestAwareEvent;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.domain.valueobjects.commands.CloseSurveyCommand;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyClosedEvent;
import knbit.events.bc.interest.survey.domain.builders.SurveyCreatedEventBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class ClosingSurveyTest {

    private FixtureConfiguration<InterestAwareEvent> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.interestAwareEventFixtureConfiguration();
    }

//    @Test
//    public void shouldCreateSurveyClosedEventOnCorrespondingCommand() throws Exception {
//
//        final EventId eventId = EventId.of("eventId");
//
//        // todo fix
//        fixture
//                .given(
//                        InterestAwareEventCreated.of(
//                                eventId, eventDetails
//                        )
//
//                        SurveyCreatedEventBuilder
//                                .instance()
//                                .surveyId(eventId)
//                                .build()
//                )
//                .when(
//                        SurveyClosedEvent.of(eventId)
//                )
//                .expectEvents(
//                        SurveyClosedEvent.of(eventId)
//                );
//
//    }
//
//    @Test
//    public void shouldThrowAnExceptionTryingToCloseClosedSurvey() throws Exception {
//
//
//        final SurveyId surveyId = SurveyId.of("surveyId");
//
//        fixture
//                .given(
//                        SurveyCreatedEventBuilder
//                                .instance()
//                                .surveyId(surveyId)
//                                .build(),
//
//                        new SurveyClosedEvent(surveyId)
//
//                )
//                .when(
//                        new CloseSurveyCommand(surveyId)
//                )
//                .expectException(IllegalStateException.class);
//
//    }
}
