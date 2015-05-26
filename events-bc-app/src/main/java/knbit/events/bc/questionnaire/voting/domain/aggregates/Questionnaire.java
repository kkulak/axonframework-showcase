package knbit.events.bc.questionnaire.voting.domain.aggregates;

import com.google.common.collect.Sets;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionnaireId;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.vote.NegativeVote;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.vote.PositiveVote;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.vote.Vote;

import java.util.Collection;

/**
 * Created by novy on 25.05.15.
 */
public class Questionnaire extends IdentifiedDomainAggregateRoot<QuestionnaireId> {

    private Collection<Vote> votes = Sets.newHashSet();


    public void voteUp(PositiveVote vote) {
    }

    public void voteDown(NegativeVote vote) {

    }
}
