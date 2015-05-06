package knbit.events.bc.eventproposal.domain.aggregates;

import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.eventproposal.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.Description;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.Name;
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposed;
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

    EventProposal(EventProposalId eventProposalId, Name name, Description description, EventType eventType) {
        apply(new EventProposed(
                        eventProposalId, name, description, eventType, ProposalState.PENDING
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
