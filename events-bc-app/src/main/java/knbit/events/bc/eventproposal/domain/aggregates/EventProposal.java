package knbit.events.bc.eventproposal.domain.aggregates;

import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.exceptions.*;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposalEvents;
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
    private EventFrequency eventFrequency;
    private ProposalState state;

    EventProposal(
            EventProposalId eventProposalId, Name name, Description description, EventType eventType, EventFrequency eventFrequency) {
        apply(new EventProposalEvents.EventProposed(
                        eventProposalId, name, description, eventType, eventFrequency, ProposalState.PENDING
                )
        );
    }

    public void accept() {
        if (state == ProposalState.ACCEPTED) {
            throw new EventProposalExceptions.ProposalAlreadyAccepted(id.value());
        }

        if (state == ProposalState.REJECTED) {
            throw new EventProposalExceptions.CannotAcceptRejectedProposal(id.value());
        }

        apply(
                new EventProposalEvents.ProposalAccepted(id, eventType, ProposalState.ACCEPTED)
        );

    }

    @EventSourcingHandler
    private void on(EventProposalEvents.ProposalAccepted event) {
        this.state = event.state();
    }

    public void reject() {
        if (state == ProposalState.REJECTED) {
            throw new EventProposalExceptions.ProposalAlreadyRejected(id.value());
        }

        if (state == ProposalState.ACCEPTED) {
            throw new EventProposalExceptions.CannotRejectAcceptedProposal(id.value());
        }

        apply(
                new EventProposalEvents.ProposalRejected(id, ProposalState.REJECTED)
        );
    }


    @EventSourcingHandler
    private void on(EventProposalEvents.ProposalRejected event) {
        this.state = event.state();
    }

    @EventSourcingHandler
    private void on(EventProposalEvents.EventProposed event) {
        this.id = event.eventProposalId();
        this.name = event.name();
        this.description = event.description();
        this.eventType = event.eventType();
        this.eventFrequency = event.eventFrequency();
        this.state = event.proposalState();
    }
}
