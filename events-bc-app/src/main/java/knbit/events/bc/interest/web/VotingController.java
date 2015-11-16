package knbit.events.bc.interest.web;

import knbit.events.bc.auth.Authorized;
import knbit.events.bc.auth.Role;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.interest.domain.valueobjects.commands.QuestionnaireCommands;
import knbit.events.bc.interest.domain.valueobjects.submittedanswer.AttendeeAnswer;
import knbit.events.bc.interest.domain.valueobjects.submittedanswer.SubmittedAnswer;
import knbit.events.bc.interest.web.forms.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/events")
public class VotingController {
    private final CommandGateway gateway;

    @Autowired
    public VotingController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(value = "/{eventId}/survey/votes", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void vote(@RequestBody @Valid VoteForm form,
                     @PathVariable String eventId) {
        final Object voteCommand = VoteCommandFactory.create(eventId, form.getAttendee(), form.getType());
        gateway.sendAndWait(voteCommand);
        form.getAnswers().ifPresent(answers -> {
            final Object command = prepareCompleteQuestionnaireCommand(eventId, form.getAttendee(), form.getAnswers());
            gateway.send(command);
        });
    }

    private Object prepareCompleteQuestionnaireCommand(
            String eventId, AttendeeDTO attendeeDTO, Optional<List<SubmittedAnswerDTO>> answers) {
        final MemberId memberId = MemberId.of(attendeeDTO.getMemberId());
        final Attendee attendee = Attendee.of(memberId);
        final EventId id = EventId.of(eventId);
        final List<SubmittedAnswerDTO> answerDTOs = answers.get();
        final List<SubmittedAnswer> submittedAnswers = answerDTOs.stream()
                .map(dto -> SubmittedAnswer.of(
                        MappingUtils.toQuestionData(dto.getQuestion()),
                        MappingUtils.toDomainAnswers(dto.getAnswers()))
                )
                .collect(Collectors.toList());
        final AttendeeAnswer attendeeAnswer = AttendeeAnswer.of(attendee, submittedAnswers);
        return QuestionnaireCommands.Complete.of(id, attendeeAnswer);
    }

}

