package knbit.events.bc.questionnaire.domain.aggregates;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.questionnaire.domain.entities.Question;
import knbit.events.bc.questionnaire.domain.valueobjects.events.QuestionnaireCreatedEvent;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.questionnaire.domain.valueobjects.vote.NegativeVote;
import knbit.events.bc.questionnaire.domain.valueobjects.vote.PositiveVote;
import knbit.events.bc.questionnaire.domain.valueobjects.vote.Vote;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;

/**
 * Created by novy on 25.05.15.
 */
public class Questionnaire extends IdentifiedDomainAggregateRoot<QuestionnaireId> {

    private EventId eventId;
    private Collection<Vote> votes = Sets.newHashSet();
    private Collection<Question> questions = Lists.newLinkedList();

    public Questionnaire() {
    }

    public Questionnaire(QuestionnaireId questionnaireId, EventId eventId) {
        apply(
                QuestionnaireCreatedEvent.of(questionnaireId,eventId)
        );

    }

    @EventSourcingHandler
    private void on(QuestionnaireCreatedEvent event) {
        id = event.questionnaireId();
        eventId = event.eventId();

    }


    public void voteUp(PositiveVote vote) {
    }

    public void voteDown(NegativeVote vote) {

    }
}
