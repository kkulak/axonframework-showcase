package knbit.events.bc.domain.proposing.event.aggregates;

import knbit.events.bc.domain.common.IdentifiedDomainAggregateRoot;
import knbit.events.bc.domain.proposing.event.valueobjects.Description;
import knbit.events.bc.domain.proposing.event.valueobjects.EventProposalId;
import knbit.events.bc.domain.proposing.event.valueobjects.Name;
import knbit.events.bc.domain.proposing.event.valueobjects.ProposalState;
import knbit.events.bc.domain.proposing.event.valueobjects.events.EventProposed;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * Created by novy on 05.05.15.
 */
public class EventProposal extends IdentifiedDomainAggregateRoot<EventProposalId> {

    private EventProposal() {
    }

    private Name name;
    private Description description;
    private ProposalState state;

    EventProposal(EventProposalId eventProposalId, Name name, Description description) {
        apply(new EventProposed(
                        eventProposalId, name, description, ProposalState.PENDING
                )
        );
    }

    @EventSourcingHandler
    public void on(EventProposed event) {
        this.id = event.id();
        this.name = event.name();
        this.description = event.description();
        this.state = event.proposalState();
    }
}
