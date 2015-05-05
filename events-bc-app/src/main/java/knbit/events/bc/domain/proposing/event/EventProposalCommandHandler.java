package knbit.events.bc.domain.proposing.event;

import knbit.events.bc.domain.proposing.event.aggregates.EventProposal;
import knbit.events.bc.domain.proposing.event.aggregates.EventProposalFactory;
import knbit.events.bc.domain.proposing.event.valueobjects.commands.ProposeEventCommand;
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
                        command.id(), command.name(), command.description()
                )
        );
    }
}
