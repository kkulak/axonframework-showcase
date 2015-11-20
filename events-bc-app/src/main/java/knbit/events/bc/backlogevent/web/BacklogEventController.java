package knbit.events.bc.backlogevent.web;

import knbit.events.bc.auth.Authorized;
import knbit.events.bc.auth.Role;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands;
import knbit.events.bc.backlogevent.web.forms.EventBacklogDTO;
import knbit.events.bc.backlogevent.web.forms.SectionDTO;
import knbit.events.bc.common.domain.valueobjects.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/events")
public class BacklogEventController {

    private final CommandGateway gateway;

    @Autowired
    public BacklogEventController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createBacklogEvent(@RequestBody @Valid EventBacklogDTO eventBacklogDTO) {
        final EventId eventId = new EventId();
        final EventDetails eventDetails = eventDetailsFrom(eventBacklogDTO);

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

    private EventDetails eventDetailsFrom(EventBacklogDTO eventBacklogDTO) {
        return EventDetails.of(
                Name.of(eventBacklogDTO.getName()),
                Description.of(eventBacklogDTO.getDescription()),
                eventBacklogDTO.getEventType(),
                urlOrNull(eventBacklogDTO.getImageUrl()),
                sectionOrNull(eventBacklogDTO.getSection())
        );
    }

    private URL urlOrNull(Optional<String> value) {
        return value
                .map(URL::of)
                .orElse(null);
    }

    private Section sectionOrNull(Optional<SectionDTO> dto) {
        return dto
                .map(value -> Section.of(value.getId(), value.getName()))
                .orElse(null);
    }

}
