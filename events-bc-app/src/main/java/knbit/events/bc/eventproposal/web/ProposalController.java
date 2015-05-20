package knbit.events.bc.eventproposal.web;

import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.commands.AcceptProposalCommand;
import knbit.events.bc.eventproposal.domain.valueobjects.commands.ProposeEventCommand;
import knbit.events.bc.eventproposal.domain.valueobjects.commands.RejectProposalCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by novy on 05.05.15.
 */

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    private final CommandGateway commandGateway;

    @Autowired
    public ProposalController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void proposeAnEvent(@RequestBody @Valid EventProposalDto proposalDto) {
        commandGateway.send(
                new ProposeEventCommand(
                        new EventProposalId(), proposalDto.getName(),
                        proposalDto.getDescription(), proposalDto.getEventType(),
                        proposalDto.getEventFrequency()
                )
        );

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public void changeProposalState(@PathVariable String id, @RequestBody @Valid ProposalStateDto proposalStateDto) {
        final ProposalStateDto.ProposalState proposalState = proposalStateDto.getState();
        final EventProposalId eventProposalId = EventProposalId.of(id);

        if (proposalState == ProposalStateDto.ProposalState.ACCEPTED) {
            acceptProposal(eventProposalId);
        } else {
            rejectProposal(eventProposalId);
        }
    }

    private void acceptProposal(EventProposalId eventProposalId) {
        commandGateway.send(
                new AcceptProposalCommand(eventProposalId)
        );
    }

    private void rejectProposal(EventProposalId eventProposalId) {
        commandGateway.send(
                new RejectProposalCommand(eventProposalId)
        );
    }
}
