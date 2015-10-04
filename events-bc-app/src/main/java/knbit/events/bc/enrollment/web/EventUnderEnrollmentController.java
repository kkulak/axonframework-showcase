package knbit.events.bc.enrollment.web;

import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.TermModifyingCommands;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.function.Function;

/**
 * Created by novy on 04.10.15.
 */

@RestController
@RequestMapping(value = "/events")
public class EventUnderEnrollmentController {

    private final CommandGateway commandGateway;

    @Autowired
    public EventUnderEnrollmentController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{eventId}/enrollment")
    public void startEnrollment(@PathVariable("eventId") String eventId,
                                @RequestBody @Valid Collection<TermDTO> terms) {

        final EventId domainEventId = EventId.of(eventId);

        transitToEnrollment(domainEventId);
        assignLecturers(domainEventId, terms);
        setParticipantLimits(domainEventId, terms);
    }

    private void transitToEnrollment(EventId eventId) {
        commandGateway.sendAndWait(
                UnderChoosingTermEventCommands.TransitToEnrollment.of(eventId)
        );
    }

    private void assignLecturers(EventId eventId, Collection<TermDTO> terms) {
        final Function<TermDTO, TermModifyingCommands.AssignLecturer> assignLecturerCommandFromDTO = termDTO -> {
            final TermDTO.Lecturer lecturerDto = termDTO.getLecturer();
            final TermId termId = TermId.of(termDTO.getTermId());

            return TermModifyingCommands.AssignLecturer.of(
                    eventId,
                    termId,
                    lecturerDto.getFirstName(),
                    lecturerDto.getLastName()
            );
        };

        terms.stream()
                .map(assignLecturerCommandFromDTO)
                .forEach(commandGateway::sendAndWait);
    }

    private void setParticipantLimits(EventId eventId, Collection<TermDTO> terms) {
        final Function<TermDTO, TermModifyingCommands.SetParticipantLimit> setLimitCommandFromDTO = termDTO -> {
            final TermId termId = TermId.of(termDTO.getTermId());
            return TermModifyingCommands.SetParticipantLimit.of(eventId, termId, termDTO.getParticipantsLimit());
        };

        terms.stream()
                .map(setLimitCommandFromDTO)
                .forEach(commandGateway::sendAndWait);
    }

}
