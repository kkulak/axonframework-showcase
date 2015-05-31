package knbit.events.bc.interest.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.builders.QuestionDataBuilder;
import knbit.events.bc.interest.builders.SurveyingInterestStartedEventBuilder;
import knbit.events.bc.interest.domain.exceptions.AlreadyHasQuestionnaireException;
import knbit.events.bc.interest.domain.exceptions.SurveyingInterestAlreadyEndedException;
import knbit.events.bc.interest.domain.exceptions.SurveyingInterestAlreadyInProgressException;
import knbit.events.bc.interest.domain.valueobjects.commands.AddQuestionnaireCommand;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated;
import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireAddedEvent;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyingInterestEndedEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.Question;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionFactory;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 31.05.15.
 */
public class AddingQuestionnaireTest {

    private FixtureConfiguration<InterestAwareEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;
    private QuestionData soleQuestionData;
    private Question soleQuestion;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.interestAwareEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
        soleQuestionData = QuestionDataBuilder
                .instance()
                .build();
        soleQuestion = QuestionFactory.newQuestion(soleQuestionData);

    }

    @Test
    public void shouldNotBeAbleToAddQuestionnaireIfSurveyingInProgress() throws Exception {

        fixture
                .given(
                        InterestAwareEventCreated.of(
                                eventId, eventDetails
                        ),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .when(
                        AddQuestionnaireCommand.of(
                                eventId,
                                ImmutableList.of(soleQuestionData)
                        )
                )
                .expectException(SurveyingInterestAlreadyInProgressException.class);

    }

    @Test
    public void shouldNotBeAbleToAddQuestionnaireIfSurveyingAlreadyEnded() throws Exception {

        fixture
                .given(
                        InterestAwareEventCreated.of(
                                eventId, eventDetails
                        ),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyingInterestEndedEvent.of(eventId)
                )
                .when(
                        AddQuestionnaireCommand.of(
                                eventId,
                                ImmutableList.of(soleQuestionData)
                        )
                )
                .expectException(SurveyingInterestAlreadyEndedException.class);
    }

    @Test
    public void shouldBeAbleToAddQuestionnaireOnlyOnce() throws Exception {

        fixture
                .given(
                        InterestAwareEventCreated.of(
                                eventId, eventDetails
                        ),

                        QuestionnaireAddedEvent.of(
                                eventId,
                                ImmutableList.of(soleQuestion)
                        )
                )
                .when(
                        AddQuestionnaireCommand.of(
                                eventId,
                                ImmutableList.of(soleQuestionData)
                        )
                )
                .expectException(AlreadyHasQuestionnaireException.class);
    }

    @Test
    public void shouldCreateCorrespondingEventOtherwise() throws Exception {

        fixture
                .given(
                        InterestAwareEventCreated.of(
                                eventId, eventDetails
                        )
                )
                .when(
                        AddQuestionnaireCommand.of(
                                eventId,
                                ImmutableList.of(soleQuestionData)
                        )
                )
                .expectEvents(
                        QuestionnaireAddedEvent.of(
                                eventId,
                                ImmutableList.of(soleQuestion)
                        )
                );
    }
}
