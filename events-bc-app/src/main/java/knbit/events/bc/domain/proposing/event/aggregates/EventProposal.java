package knbit.events.bc.domain.proposing.event.aggregates;

import knbit.events.bc.domain.common.IdentifiedDomainAggregateRoot;
import knbit.events.bc.domain.proposing.event.valueobjects.Description;
import knbit.events.bc.domain.proposing.event.valueobjects.EventProposalId;
import knbit.events.bc.domain.proposing.event.valueobjects.Name;
import knbit.events.bc.domain.proposing.event.valueobjects.ProposalState;

/**
 * Created by novy on 05.05.15.
 */
public class EventProposal extends IdentifiedDomainAggregateRoot<EventProposalId> {

    public EventProposal() {
    }

    private Name name;
    private Description description;
    private ProposalState state;

    public EventProposal(EventProposalId eventProposalId, Name name, Description description) {
        this.id = eventProposalId;
        this.name = name;
        this.description = description;
        this.state = ProposalState.PENDING;
    }
}
