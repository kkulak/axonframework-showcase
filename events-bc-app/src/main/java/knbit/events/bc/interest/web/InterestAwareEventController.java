package knbit.events.bc.interest.web;

import knbit.events.bc.auth.Authorized;
import knbit.events.bc.auth.Role;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.commands.QuestionnaireCommands;
import knbit.events.bc.interest.domain.valueobjects.commands.SurveyCommands;
import knbit.events.bc.interest.web.forms.QuestionDataDTO;
import knbit.events.bc.interest.web.forms.SurveyForm;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static knbit.events.bc.interest.web.forms.MappingUtils.toQuestionData;

@RestController
@RequestMapping(value = "/events")
public class InterestAwareEventController {
    private final CommandGateway gateway;

    @Autowired
    public InterestAwareEventController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(value = "/{eventId}/survey", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createSurvey(@PathVariable("eventId") String eventId,
                             @RequestBody @Valid SurveyForm form) {
        final EventId id = EventId.of(eventId);

        transitFromBacklog(id);
        addQuestionnaireIfAnyQuestionsPresent(id, form);
        startSurveying(id, form);
    }

    private void transitFromBacklog(EventId id) {
        gateway.sendAndWait(BacklogEventCommands.TransitToInterestAwareEventCommand.of(id));
    }

    private void addQuestionnaireIfAnyQuestionsPresent(EventId id, SurveyForm form) {
        final List<QuestionDataDTO> questions = form.getQuestions();
        if (!questions.isEmpty()) {
            gateway.sendAndWait(
                    QuestionnaireCommands.Add.of(id, toQuestionData(questions))
            );
        }
    }

    private void startSurveying(EventId id, SurveyForm form) {
        gateway.sendAndWait(
                SurveyCommands.Start.of(
                        id, form.getMinimalInterestThreshold(), form.getEndOfSurveying()
                )
        );
    }

}
