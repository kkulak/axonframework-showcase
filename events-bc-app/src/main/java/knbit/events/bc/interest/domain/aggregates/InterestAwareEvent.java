package knbit.events.bc.interest.domain.aggregates;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.enums.InterestAwareEventState;
import knbit.events.bc.interest.domain.exceptions.*;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireEvents;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyStartingEvents;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.factory.SurveyStartedEventFactory;
import knbit.events.bc.interest.domain.valueobjects.question.Question;
import knbit.events.bc.interest.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.domain.valueobjects.question.QuestionFactory;
import knbit.events.bc.interest.domain.valueobjects.question.answer.AnsweredQuestion;
import knbit.events.bc.interest.domain.valueobjects.submittedanswer.AttendeeAnswer;
import knbit.events.bc.interest.domain.valueobjects.submittedanswer.SubmittedAnswer;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static knbit.events.bc.interest.domain.enums.InterestAwareEventState.*;

/**
 * Created by novy on 28.05.15.
 */
public class InterestAwareEvent extends IdentifiedDomainAggregateRoot<EventId> {

    private EventDetails eventDetails;

    private Collection<Attendee> positiveVoters = Sets.newHashSet();
    private Collection<Attendee> negativeVoters = Sets.newHashSet();
    private InterestPolicy interestPolicy;

    private Map<QuestionData, Question> questionnaire = Maps.newHashMap();
    private Collection<Attendee> interviewees = Sets.newHashSet();

    private InterestAwareEventState state;

    private InterestAwareEvent() {
    }

    public InterestAwareEvent(EventId eventId, EventDetails eventDetails) {
        apply(
                InterestAwareEvents.Created.of(eventId, eventDetails)
        );
    }

    @EventSourcingHandler
    private void on(InterestAwareEvents.Created event) {
        this.id = event.eventId();
        this.eventDetails = event.eventDetails();

        this.state = CREATED;
    }

    public void voteUp(Attendee attendee) {
        rejectIfVotingNotAllowed(attendee);

        if (interestPolicy.reachedBy(positiveVoters.size() + 1)) {
            apply(SurveyEvents.InterestThresholdReached.of(id, eventDetails));
        }

        apply(SurveyEvents.VotedUp.of(id, attendee));

    }

    public void voteDown(Attendee attendee) {
        rejectIfVotingNotAllowed(attendee);

        apply(SurveyEvents.VotedDown.of(id, attendee));
    }

    private void rejectIfVotingNotAllowed(Attendee attendee) {
        rejectOnInvalidStates(CREATED, ENDED, TRANSITED);
        rejectOnAlreadyVoted(attendee);
    }

    public void startSurveying(InterestPolicy interestPolicy, SurveyStartedEventFactory factory) {
        rejectOnInvalidStates(IN_PROGRESS, ENDED, TRANSITED);

        apply(
                factory.newSurveyingInterestStartedEvent(id, eventDetails, interestPolicy)
        );
    }

    public void endSurveying() {
        rejectOnInvalidStates(CREATED, ENDED, TRANSITED);

        apply(SurveyEvents.Ended.of(id));
    }

    @EventSourcingHandler
    private void on(SurveyStartingEvents.Started event) {
        this.interestPolicy = event.interestPolicy();
        this.state = IN_PROGRESS;
    }

    @EventSourcingHandler
    private void on(SurveyEvents.Ended event) {
        this.state = ENDED;
    }

    @EventSourcingHandler
    private void on(SurveyEvents.VotedUp event) {
        positiveVoters.add(event.attendee());
    }

    @EventSourcingHandler
    private void on(SurveyEvents.VotedDown event) {
        negativeVoters.add(event.attendee());
    }

    private void rejectOnAlreadyVoted(Attendee attendee) {
        if (attendeeAlreadyVoted(attendee)) {
            throw new SurveyAlreadyVotedException(id, attendee);
        }
    }

    private boolean attendeeAlreadyVoted(Attendee attendee) {
        return votedUp(attendee) || votedDown(attendee);
    }

    private boolean votedUp(Attendee attendee) {
        return positiveVoters.contains(attendee);
    }

    private boolean votedDown(Attendee attendee) {
        return negativeVoters.contains(attendee);
    }

    private void rejectOnInvalidStates(InterestAwareEventState... states) {
        Stream.of(states)
                .forEach(this::rejectOn);
    }

    private void rejectOn(InterestAwareEventState incorrectState) {
        if (state == incorrectState) {
            throw incorrectState.correspondingIncorrectStateException(id);
        }
    }

    public void addQuestionnaire(List<QuestionData> questionData) {
        rejectOnInvalidStates(IN_PROGRESS, ENDED, TRANSITED);
        rejectOnQuestionnaireAlreadyExists();

        apply(
                QuestionnaireEvents.Added.of(id, questionsFrom(questionData))
        );
    }

    public void completeQuestionnaire(AttendeeAnswer attendeeAnswer) {
        rejectOnCompletingQuestionnaireNotAllowed(attendeeAnswer.attendee());

        final List<SubmittedAnswer> answers = attendeeAnswer.submittedAnswers();
        Preconditions.checkArgument(answers.size() == questionnaire.size(), "Wrong number of answers");

        final List<AnsweredQuestion> answeredQuestions = answers
                .stream()
                .map(submittedAnswer -> {
                    final Question question = questionnaire.get(submittedAnswer.questionData());
                    return question.answer(submittedAnswer);
                })
                .collect(Collectors.toList());

        apply(QuestionnaireEvents.CompletedByAttendee.of(id, attendeeAnswer.attendee(), answeredQuestions));
    }

    @EventSourcingHandler
    private void on(QuestionnaireEvents.CompletedByAttendee event) {
        interviewees.add(event.attendee());
    }

    public void transitToUnderChoosingTermEvent() {
        rejectOnInvalidStates(CREATED, TRANSITED);
        endIfSurveyingInProgress();

        apply(InterestAwareEvents.TransitedToUnderChoosingTerm.of(id, eventDetails));
    }

    private void endIfSurveyingInProgress() {
        if (state == IN_PROGRESS) {
            endSurveying();
        }
    }

    @EventSourcingHandler
    private void on(InterestAwareEvents.TransitedToUnderChoosingTerm event) {
        state = TRANSITED;
    }

    private void rejectOnCompletingQuestionnaireNotAllowed(Attendee attendee) {
        rejectOnInvalidStates(CREATED, ENDED, TRANSITED);
        rejectOnQuestionnaireDoesNotExist();
        rejectOnVotedDown(attendee);
        rejectOnNotYetVotedUp(attendee);
        rejectOnAttendeeAlreadyCompletedQuestionnaire(attendee);
    }

    private void rejectOnQuestionnaireDoesNotExist() {
        if (questionnaire.isEmpty()) {
            throw new NoQuestionnaireSetException(id);
        }
    }

    private void rejectOnAttendeeAlreadyCompletedQuestionnaire(Attendee attendee) {
        if (interviewees.contains(attendee)) {
            throw new AttendeeAlreadyCompletedQuestionnaireException(id, attendee);
        }
    }

    private void rejectOnVotedDown(Attendee attendee) {
        if (votedDown(attendee)) {
            throw new VotedDownBeforeException(id, attendee);
        }
    }

    private void rejectOnNotYetVotedUp(Attendee attendee) {
        if (!votedUp(attendee)) {
            throw new NotVotedUpException(id, attendee);
        }
    }

    @EventSourcingHandler
    private void on(QuestionnaireEvents.Added event) {
        event.questions()
                .forEach(
                        question -> questionnaire.put(
                                question.questionData(), question
                        )
                );

    }

    private void rejectOnQuestionnaireAlreadyExists() {
        if (!questionnaire.isEmpty()) {
            throw new AlreadyHasQuestionnaireException(id);
        }
    }

    private List<Question> questionsFrom(List<QuestionData> questionData) {
        return questionData
                .stream()
                .map(QuestionFactory::newQuestion)
                .collect(Collectors.toList());
    }
}
