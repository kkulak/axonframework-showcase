package knbit.events.bc.backlogevent.web;

import knbit.events.bc.auth.Authorized;
import knbit.events.bc.auth.Role;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands;
import knbit.events.bc.backlogevent.web.forms.EventBacklogDTO;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/events")
public class BacklogEventController {

    private final CommandGateway gateway;
    private final EventDetailsDTOTransformer dtoTransformer;

    @Autowired
    public BacklogEventController(CommandGateway gateway, EventDetailsDTOTransformer dtoTransformer) {
        this.gateway = gateway;
        this.dtoTransformer = dtoTransformer;
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createBacklogEvent(@RequestBody @Valid EventBacklogDTO eventBacklogDTO) {
        final EventId eventId = new EventId();
        final EventDetails eventDetails = dtoTransformer.eventDetailsFrom(eventBacklogDTO);

        gateway.send(
                BacklogEventCommands.Create.of(eventId, eventDetails)
        );
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(value = "/{eventId}/backlog", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancelBacklogEvent(@PathVariable("eventId") String eventId) {
        final EventId domainId = EventId.of(eventId);

        gateway.send(
                BacklogEventCommands.Cancel.of(domainId)
        );
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(value = "/{eventId}/backlog/details", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeEventDetails(@PathVariable("eventId") String eventId, @RequestBody @Valid EventBacklogDTO newDetailsDTO) {
        final EventId domainId = EventId.of(eventId);
        final EventDetails newEventDetails = dtoTransformer.eventDetailsFrom(newDetailsDTO);

        gateway.send(
                BacklogEventCommands.ChangeDetails.of(domainId, newEventDetails)
        );
    }
}
