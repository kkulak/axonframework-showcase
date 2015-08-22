package knbit.events.bc.interest.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.*;
import knbit.events.bc.interest.domain.exceptions.*;
import knbit.events.bc.interest.domain.valueobjects.commands.QuestionnaireCommands;
import knbit.events.bc.interest.domain.valueobjects.events.*;
import knbit.events.bc.interest.domain.enums.AnswerType;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.interest.domain.valueobjects.question.Question;
import knbit.events.bc.interest.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.domain.valueobjects.question.QuestionFactory;
import knbit.events.bc.interest.domain.valueobjects.question.answer.AnsweredQuestion;
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer;
import knbit.events.bc.interest.domain.valueobjects.submittedanswer.AttendeeAnswer;
import knbit.events.bc.interest.domain.valueobjects.submittedanswer.SubmittedAnswer;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 31.05.15.
 */
public class CompletingQuestionnaireTest {

    private FixtureConfiguration<InterestAwareEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;
    private QuestionData soleQuestionData;
    private Question soleQuestion;
    private SubmittedAnswer soleAnswer;
    private AttendeeAnswer attendeeAnswer;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.interestAwareEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();

        soleQuestionData = QuestionDataBuilder
                .instance()
                .answerType(AnswerType.MULTIPLE_CHOICE)
                .answer(DomainAnswer.of("opt1"))
                .answer(DomainAnswer.of("opt2"))
                .answer(DomainAnswer.of("opt3"))
                .build();
        soleQuestion = QuestionFactory.newQuestion(soleQuestionData);

        soleAnswer = SubmittedAnswer.of(
                soleQuestionData,
                ImmutableList.of(DomainAnswer.of("opt1"), DomainAnswer.of("opt3"))
        );

        attendeeAnswer = AttendeeAnswer.of(
                Attendee.of("firstname", "lastname"),
                ImmutableList.of(soleAnswer)
        );


    }

    @Test
    public void shouldNotBeAbleToCompleteQuestionnaireBeforeSurveyingStarted() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        )
                )
                .when(
                        QuestionnaireCommands.Complete.of(eventId, attendeeAnswer)

                )
                .expectException(SurveyingInterestNotYetStartedException.class);

    }

    @Test
    public void shouldNotBeAbleToCompleteQuestionnaireAfterSurveyingEnded() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        QuestionnaireEvents.Added.of(
                                eventId,
                                ImmutableList.of(soleQuestion)
                        ),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyEvents.Ended.of(eventId)
                )
                .when(
                        QuestionnaireCommands.Complete.of(eventId, attendeeAnswer)

                )
                .expectException(SurveyingInterestAlreadyEndedException.class);

    }

    @Test
    public void shouldNotBeAbleToCompleteQuestionnaireOnEventTransitedToUnderChoosingTermEvent() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        InterestAwareEvents.TransitedToUnderChoosingTerm.of(
                                eventId, eventDetails
                        )
                )
                .when(
                        QuestionnaireCommands.Complete.of(eventId, attendeeAnswer)

                )
                .expectException(InterestAwareEventAlreadyTransitedException.class);

    }

    @Test
    public void shouldNotBeAbleToCompleteNonExistingQuestionnaire() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .when(
                        QuestionnaireCommands.Complete.of(eventId, attendeeAnswer)

                )
                .expectException(NoQuestionnaireSetException.class);
    }

    @Test
    public void shouldNotBeAbleToCompleteQuestionnaireBeforeVotingUp() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        QuestionnaireEvents.Added.of(
                                eventId,
                                ImmutableList.of(soleQuestion)
                        ),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build()
                )
                .when(
                        QuestionnaireCommands.Complete.of(eventId, attendeeAnswer)

                )
                .expectException(NotVotedUpException.class);

    }

    @Test
    public void shouldNotBeAbleToCompleteQuestionnaireAfterVotingDown() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        QuestionnaireEvents.Added.of(
                                eventId,
                                ImmutableList.of(soleQuestion)
                        ),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyVotedDownEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendeeAnswer.attendee())
                                .build()
                )
                .when(
                        QuestionnaireCommands.Complete.of(eventId, attendeeAnswer)

                )
                .expectException(VotedDownBeforeException.class);
    }

    @Test
    public void shouldNotBeAbleToCompleteQuestionnaireMoreThanOnce() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        QuestionnaireEvents.Added.of(
                                eventId,
                                ImmutableList.of(soleQuestion)
                        ),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendeeAnswer.attendee())
                                .build(),

                        QuestionnaireCompletedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendeeAnswer.attendee())
                                .answeredQuestion(
                                        AnsweredQuestion.of(
                                                soleQuestionData,
                                                soleAnswer.answers()
                                        )
                                )
                                .build()
                )
                .when(
                        QuestionnaireCommands.Complete.of(eventId, attendeeAnswer)

                )
                .expectException(AttendeeAlreadyCompletedQuestionnaireException.class);

    }

    @Test
    public void shouldProduceCorrespondingEventOtherwise() throws Exception {

        fixture
                .given(
                        InterestAwareEvents.Created.of(
                                eventId, eventDetails
                        ),

                        QuestionnaireEvents.Added.of(
                                eventId,
                                ImmutableList.of(soleQuestion)
                        ),

                        SurveyingInterestStartedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .build(),

                        SurveyVotedUpEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendeeAnswer.attendee())
                                .build()
                )
                .when(
                        QuestionnaireCommands.Complete.of(eventId, attendeeAnswer)

                )
                .expectEvents(
                        QuestionnaireCompletedEventBuilder
                                .instance()
                                .eventId(eventId)
                                .attendee(attendeeAnswer.attendee())
                                .answeredQuestion(
                                        AnsweredQuestion.of(
                                                soleQuestionData,
                                                soleAnswer.answers()
                                        )
                                )
                                .build()
                );

    }
}
