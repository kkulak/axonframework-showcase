package knbit.events.bc.choosingterm.web;

import knbit.events.bc.backlogevent.domain.valueobjects.commands.TransitBacklogEventToUnderChoosingTermEventCommand;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.commands.TransitInterestAwareEventToUnderTermChoosingEventCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by novy on 16.08.15.
 */

@RestController
@RequestMapping("/events")
public class UnderChoosingTermEventController {

    private final CommandGateway commandGateway;

    @Autowired
    public UnderChoosingTermEventController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{eventId}/choosing-term")
    public void startChoosingTerm(@PathVariable("eventId") String eventId,
                                  @RequestParam("fromState") TransitFrom previousState) {

        final EventId id = EventId.of(eventId);

        transitFrom(previousState, id);


    }

    private void transitFrom(TransitFrom previousState, EventId eventId) {
        if (previousState == TransitFrom.BACKLOG) {
            transitFromBacklogEvent(eventId);
        } else {
            transitFromInterestAwareEvent(eventId);
        }
    }

    private void transitFromBacklogEvent(EventId eventId) {
        commandGateway.sendAndWait(
                TransitBacklogEventToUnderChoosingTermEventCommand.of(eventId)
        );
    }

    private void transitFromInterestAwareEvent(EventId eventId) {
        commandGateway.sendAndWait(
                TransitInterestAwareEventToUnderTermChoosingEventCommand.of(eventId)
        );
    }

    enum TransitFrom {
        BACKLOG, SURVEY_INTEREST
    }
}
