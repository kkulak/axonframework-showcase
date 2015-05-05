package knbit.events.bc.domain.proposing.event.web;

import knbit.events.bc.domain.proposing.event.valueobjects.EventProposalId;
import knbit.events.bc.domain.proposing.event.valueobjects.commands.ProposeEventCommand;
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

    // todo: location?
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void proposeAnEvent(@RequestBody @Valid EventProposalDto proposalDto) {
        commandGateway.send(
                new ProposeEventCommand(
                        new EventProposalId(), proposalDto.getName(), proposalDto.getDescription(), proposalDto.getEventType()
                )
        );

    }
}
