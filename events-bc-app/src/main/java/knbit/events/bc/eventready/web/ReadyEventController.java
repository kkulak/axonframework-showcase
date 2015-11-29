package knbit.events.bc.eventready.web;

import knbit.events.bc.auth.Authorized;
import knbit.events.bc.auth.Role;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import knbit.events.bc.eventready.domain.valueobjects.ReadyCommands;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by novy on 24.10.15.
 */

@RestController
@RequestMapping(value = "/events")
public class ReadyEventController {

    private final CommandGateway commandGateway;

    @Autowired
    public ReadyEventController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(method = RequestMethod.POST, value = "/{eventId}/ready")
    public void markReady(@PathVariable("eventId") String eventId) {

        final EventId domainEventId = EventId.of(eventId);

        commandGateway.sendAndWait(
                EventUnderEnrollmentCommands.TransitToReady.of(domainEventId)
        );
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{eventId}/ready")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancel(@PathVariable("eventId") String eventId) {

        final ReadyEventId domainEventId = ReadyEventId.of(eventId);

        commandGateway.sendAndWait(
                ReadyCommands.Cancel.of(domainEventId)
        );
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(method = RequestMethod.PUT, value = "/{eventId}/ready/details")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeDetails(@PathVariable("eventId") String eventId) {

        final ReadyEventId domainEventId = ReadyEventId.of(eventId);
        // todo: implementme
    }
}
