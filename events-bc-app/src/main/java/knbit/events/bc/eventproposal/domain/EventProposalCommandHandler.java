package knbit.events.bc.eventproposal.domain;

import knbit.events.bc.eventproposal.domain.aggregates.EventProposal;
import knbit.events.bc.eventproposal.domain.aggregates.EventProposalFactory;
import knbit.events.bc.eventproposal.domain.valueobjects.commands.AcceptProposalCommand;
import knbit.events.bc.eventproposal.domain.valueobjects.commands.ProposeEventCommand;
import knbit.events.bc.eventproposal.domain.valueobjects.commands.RejectProposalCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 05.05.15.
 */

@Component
public class EventProposalCommandHandler {

    private final Repository<EventProposal> repository;

    @Autowired
    public EventProposalCommandHandler(@Qualifier("eventProposalRepository") Repository<EventProposal> repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(ProposeEventCommand command) {
        repository.add(
                EventProposalFactory.newEventProposal(
                        command.eventProposalId(), command.name(), command.description(), command.eventType(), command.eventFrequency()
                )
        );
    }

    @CommandHandler
    public void handle(AcceptProposalCommand command) {
        final EventProposal eventProposal = repository.load(command.eventProposalId());
        eventProposal.accept();
    }

    @CommandHandler
    public void handle(RejectProposalCommand command) {
        final EventProposal eventProposal = repository.load(command.eventProposalId());
        eventProposal.reject();
    }
}
