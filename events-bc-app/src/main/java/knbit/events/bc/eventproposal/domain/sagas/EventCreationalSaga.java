package knbit.events.bc.eventproposal.domain.sagas;

import knbit.events.bc.backlogevent.domain.valueobjects.commands.CreateBacklogEventCommand;
import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposed;
import knbit.events.bc.eventproposal.domain.valueobjects.events.ProposalAcceptedEvent;
import knbit.events.bc.eventproposal.domain.valueobjects.events.ProposalRejectedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.EndSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by novy on 07.05.15.
 */

public class EventCreationalSaga extends AbstractAnnotatedSaga {

    private EventProposalId eventProposalId;
    private Name proposalName;
    private Description proposalDescription;
    private EventType proposalType;
    private EventFrequency eventFrequency;

    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "eventProposalId")
    public void handle(EventProposed event) {
        this.eventProposalId = event.eventProposalId();
        this.proposalName = event.name();
        this.proposalDescription = event.description();
        this.proposalType = event.eventType();
        this.eventFrequency = event.eventFrequency();
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "eventProposalId")
    public void handle(ProposalAcceptedEvent event) {
        commandGateway.send(
                new CreateBacklogEventCommand(
                        new EventId(),
                        EventDetails.of(
                                proposalName,
                                proposalDescription,
                                proposalType,
                                eventFrequency
                        )
                )
        );
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "eventProposalId")
    public void handle(ProposalRejectedEvent event) {
    }

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
}
