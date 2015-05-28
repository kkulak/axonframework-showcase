package knbit.events.bc.interest.survey.domain.aggreagates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.interest.survey.domain.builders.SurveyCreatedEventBuilder;
import knbit.events.bc.interest.survey.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.valueobjects.commands.CloseSurveyCommand;
import knbit.events.bc.interest.survey.domain.valueobjects.events.SurveyClosedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 28.05.15.
 */
public class ClosingSurveyTest {

    private FixtureConfiguration<Survey> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.surveyFixtureConfiguration();
    }

    @Test
    public void shouldCreateSurveyClosedEventOnCorrespondingCommand() throws Exception {

        final SurveyId surveyId = SurveyId.of("surveyId");

        fixture
                .given(
                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .build()
                )
                .when(
                        new CloseSurveyCommand(surveyId)
                )
                .expectEvents(
                        new SurveyClosedEvent(surveyId)
                );

    }

    @Test
    public void shouldThrowAnExceptionTryingToCloseClosedSurvey() throws Exception {


        final SurveyId surveyId = SurveyId.of("surveyId");

        fixture
                .given(
                        SurveyCreatedEventBuilder
                                .instance()
                                .surveyId(surveyId)
                                .build(),

                        new SurveyClosedEvent(surveyId)

                )
                .when(
                        new CloseSurveyCommand(surveyId)
                )
                .expectException(IllegalStateException.class);

    }
}
