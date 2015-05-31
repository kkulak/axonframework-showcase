package knbit.events.bc.backlogevent.web;

import knbit.events.bc.backlogevent.domain.valueobjects.commands.CreateBacklogEventCommand;
import knbit.events.bc.backlogevent.web.forms.EventBacklogDTO;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Name;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/event")
public class BacklogEventController {

    private final CommandGateway gateway;

    @Autowired
    public BacklogEventController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createBacklogEvent(@RequestBody @Valid EventBacklogDTO eventBacklogDTO) {
        gateway.send(
                new CreateBacklogEventCommand(
                        new EventId(), EventDetails.of(
                        Name.of(eventBacklogDTO.getName()), Description.of(eventBacklogDTO.getDescription()),
                        eventBacklogDTO.getEventType(), eventBacklogDTO.getEventFrequency())
                )
        );
    }

}