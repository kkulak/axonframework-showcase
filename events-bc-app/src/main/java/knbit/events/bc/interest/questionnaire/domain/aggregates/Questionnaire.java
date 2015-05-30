package knbit.events.bc.interest.questionnaire.domain.aggregates;

import com.codepoetics.protonpack.StreamUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.common.domain.enums.State;
import knbit.events.bc.interest.questionnaire.domain.exceptions.CannotAnswerClosedQuestionnaireException;
import knbit.events.bc.interest.questionnaire.domain.exceptions.QuestionnaireAlreadyAnsweredException;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.events.QuestionnaireAnsweredEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.events.QuestionnaireClosedEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.events.QuestionnaireCreatedEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.Question;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionFactory;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.AnsweredQuestion;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.AttendeeAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.SubmittedAnswer;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by novy on 25.05.15.
 */
public class Questionnaire extends IdentifiedDomainAggregateRoot<QuestionnaireId> {

    private State state;

    private EventId eventId;
    private Collection<Attendee> interviewees = Sets.newHashSet();
    private Collection<Question> questions = Lists.newLinkedList();

    private Questionnaire() {
    }

    public Questionnaire(QuestionnaireId questionnaireId, EventId eventId, List<QuestionData> questions) {
        apply(
                QuestionnaireCreatedEvent.of(questionnaireId, eventId, questions)
        );
    }

    @EventSourcingHandler
    private void on(QuestionnaireCreatedEvent event) {
        id = event.questionnaireId();
        eventId = event.eventId();

        questions.addAll(
                event.questions()
                        .stream()
                        .map(QuestionFactory::newQuestion)
                        .collect(Collectors.toList())
        );

        state = State.PENDING;
    }

    @EventSourcingHandler
    private void on(QuestionnaireClosedEvent event) {
        state = State.CLOSED;
    }

    public void answerQuestion(AttendeeAnswer attendeeAnswer) {
        rejectOnClosed();
        checkIfAttendeeAlreadyAnswered(attendeeAnswer.attendee());

        final List<SubmittedAnswer> answers = attendeeAnswer.submittedAnswers();
        Preconditions.checkArgument(answers.size() == questions.size());

        final List<AnsweredQuestion> answeredQuestions = StreamUtils.zip(

                questions.stream(),
                answers.stream(),
                Question::answer

        ).collect(Collectors.toList());

        apply(new QuestionnaireAnsweredEvent(id, attendeeAnswer.attendee(), answeredQuestions));

    }

    private void checkIfAttendeeAlreadyAnswered(Attendee attendee) {
        if (interviewees.contains(attendee)) {
            throw new QuestionnaireAlreadyAnsweredException(id, attendee);
        }
    }

    private void rejectOnClosed() {
        if (state == State.CLOSED) {
            throw new CannotAnswerClosedQuestionnaireException(id);
        }
    }

    @EventSourcingHandler
    private void on(QuestionnaireAnsweredEvent event) {
        interviewees.add(event.attendee());
    }

    public void close() {
        Preconditions.checkState(state == State.PENDING, "Questionnaire already closed!");
        apply(new QuestionnaireClosedEvent(id));
    }
}
