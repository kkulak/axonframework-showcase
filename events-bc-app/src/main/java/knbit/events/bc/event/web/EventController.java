package knbit.events.bc.event.web;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.valueobjects.commands.CreateEventCommand;
import knbit.events.bc.event.web.forms.EventDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/event")
public class EventController {

    private final CommandGateway gateway;

    @Autowired
    public EventController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody @Valid EventDTO eventDTO) {
        gateway.send(
                new CreateEventCommand(
                        new EventId(), eventDTO.getName(), eventDTO.getDescription(),
                        eventDTO.getEventType(), eventDTO.getEventFrequency()
                )
        );
    }

}
