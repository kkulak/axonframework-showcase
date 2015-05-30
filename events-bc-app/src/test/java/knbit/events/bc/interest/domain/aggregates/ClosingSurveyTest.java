package knbit.events.bc.interest.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;

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
