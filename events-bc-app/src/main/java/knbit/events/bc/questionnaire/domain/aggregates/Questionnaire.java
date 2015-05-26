package knbit.events.bc.questionnaire.domain.aggregates;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.questionnaire.domain.entities.Question;
import knbit.events.bc.questionnaire.domain.entities.QuestionFactory;
import knbit.events.bc.questionnaire.domain.exceptions.QuestionnaireAlreadyVotedException;
import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.questionnaire.domain.valueobjects.events.QuestionnaireCreatedEvent;
import knbit.events.bc.questionnaire.domain.valueobjects.events.QuestionnaireVotedDownEvent;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.questionnaire.domain.valueobjects.question.IdentifiedQuestionData;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionData;
import knbit.events.bc.questionnaire.domain.valueobjects.vote.NegativeVote;
import knbit.events.bc.questionnaire.domain.valueobjects.vote.PositiveVote;
import knbit.events.bc.questionnaire.domain.valueobjects.vote.Vote;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by novy on 25.05.15.
 */
public class Questionnaire extends IdentifiedDomainAggregateRoot<QuestionnaireId> {

    private EventId eventId;
    private Collection<Vote> votes = Sets.newHashSet();
    private Collection<Question> questions = Lists.newLinkedList();

    public Questionnaire() {
    }

    public Questionnaire(QuestionnaireId questionnaireId, EventId eventId, List<QuestionData> questions) {
        final List<IdentifiedQuestionData> identifiedQuestionData = identifyQuestions(questions);

        apply(QuestionnaireCreatedEvent.of(questionnaireId, eventId, identifiedQuestionData));
    }

    private List<IdentifiedQuestionData> identifyQuestions(List<QuestionData> questions) {
        return questions
                .stream()
                .map(questionData -> IdentifiedQuestionData.of(randomQuestionId(), questionData))
                .collect(Collectors.toList());
    }

    private QuestionId randomQuestionId() {
        return new QuestionId();
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
    }


    public void voteUp(PositiveVote vote) {
    }

    public void voteDown(NegativeVote vote) {
        final Attendee attendee = vote.attendee();
        if (attendeeAlreadyVoted(attendee)) {
            throw new QuestionnaireAlreadyVotedException(id, attendee);
        }

        apply(new QuestionnaireVotedDownEvent(id, attendee));
    }

    @EventSourcingHandler
    private void on(QuestionnaireVotedDownEvent event) {
        votes.add(new NegativeVote(event.attendee(), id));
    }

    private boolean attendeeAlreadyVoted(Attendee attendee) {
        return votes
                .stream()
                .anyMatch(
                        vote -> vote.attendee().equals(attendee)
                );
    }
}
