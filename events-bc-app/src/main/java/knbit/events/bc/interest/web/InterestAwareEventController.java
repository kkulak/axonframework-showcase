package knbit.events.bc.interest.web;

import knbit.events.bc.backlogevent.domain.valueobjects.commands.DeactivateBacklogEventCommand;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.web.forms.SurveyForm;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/events")
public class InterestAwareEventController {
    private final CommandGateway gateway;

    @Autowired
    public InterestAwareEventController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @RequestMapping(value = "/{eventId}/survey", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createSurvey(@PathVariable("eventId") String eventId,
                             @RequestBody @Valid SurveyForm form) {
        final EventId id = EventId.of(eventId);

        gateway.sendAndWait(DeactivateBacklogEventCommand.of(id));
    }

}
