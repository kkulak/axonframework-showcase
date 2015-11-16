package knbit.events.bc.eventready.web;

import knbit.events.bc.auth.Authorized;
import knbit.events.bc.auth.Role;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
