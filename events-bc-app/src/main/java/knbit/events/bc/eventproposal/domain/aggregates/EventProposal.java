package knbit.events.bc.eventproposal.domain.aggregates;

import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.eventproposal.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.exceptions.CannotAcceptRejectedProposalException;
import knbit.events.bc.eventproposal.domain.exceptions.CannotRejectAcceptedProposalException;
import knbit.events.bc.eventproposal.domain.exceptions.ProposalAlreadyAcceptedException;
import knbit.events.bc.eventproposal.domain.exceptions.ProposalAlreadyRejectedException;
import knbit.events.bc.eventproposal.domain.valueobjects.Description;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.Name;
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposed;
import knbit.events.bc.eventproposal.domain.valueobjects.events.ProposalAcceptedEvent;
import knbit.events.bc.eventproposal.domain.valueobjects.events.ProposalRejectedEvent;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * Created by novy on 05.05.15.
 */
public class EventProposal extends IdentifiedDomainAggregateRoot<EventProposalId> {

    private EventProposal() {
    }

    private Name name;
    private Description description;
    private EventType eventType;
    private ProposalState state;

    EventProposal(EventProposalId eventProposalId, Name name, Description description, EventType eventType) {
        apply(new EventProposed(
                        eventProposalId, name, description, eventType, ProposalState.PENDING
                )
        );
    }

    public void accept() {
        if (state == ProposalState.ACCEPTED) {
            throw new ProposalAlreadyAcceptedException(id.value());
        }

        if (state == ProposalState.REJECTED) {
            throw new CannotAcceptRejectedProposalException(id.value());
        }

        apply(
                new ProposalAcceptedEvent(id, eventType, ProposalState.ACCEPTED)
        );

    }

    @EventSourcingHandler
    public void on(ProposalAcceptedEvent event) {
        this.state = event.state();
    }

    public void reject() {
        if (state == ProposalState.REJECTED) {
            throw new ProposalAlreadyRejectedException(id.value());
        }

        if (state == ProposalState.ACCEPTED) {
            throw new CannotRejectAcceptedProposalException(id.value());
        }

        apply(
                new ProposalRejectedEvent(id, ProposalState.REJECTED)
        );
    }


    @EventSourcingHandler
    public void on(ProposalRejectedEvent event) {
        this.state = event.state();
    }

    @EventSourcingHandler
    public void on(EventProposed event) {
        this.id = event.eventProposalId();
        this.name = event.name();
        this.description = event.description();
        this.eventType = event.eventType();
        this.state = event.proposalState();
    }
}
