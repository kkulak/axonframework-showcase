package knbit.events.bc.enrollment.web;

import knbit.events.bc.auth.Authorized;
import knbit.events.bc.auth.Role;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EnrollmentCommands;
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

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(method = RequestMethod.POST, value = "/{eventId}/enrollment")
    public void startEnrollment(@PathVariable("eventId") String eventId,
                                @RequestBody @Valid Collection<TermDTO> terms) {

        final EventId domainEventId = EventId.of(eventId);

        transitToEnrollment(domainEventId);
        assignLecturers(domainEventId, terms);
        setParticipantLimits(domainEventId, terms);
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(method = RequestMethod.PUT, value = "/{eventId}/terms/{termId}/enroll/{memberId}")
    public void enrollForTerm(@PathVariable("eventId") String eventId,
                              @PathVariable("termId") String termId,
                              @PathVariable("memberId") String memberId) {

        final EventId domainEventId = EventId.of(eventId);
        final TermId domainTermId = TermId.of(termId);
        final MemberId domainMemberId = MemberId.of(memberId);

        commandGateway.sendAndWait(
                EnrollmentCommands.EnrollFor.of(domainEventId, domainTermId, domainMemberId)
        );
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{eventId}/terms/{termId}/disenroll/{memberId}")
    public void disenrollFrom(@PathVariable("eventId") String eventId,
                              @PathVariable("termId") String termId,
                              @PathVariable("memberId") String memberId) {

        final EventId domainEventId = EventId.of(eventId);
        final TermId domainTermId = TermId.of(termId);
        final MemberId domainMemberId = MemberId.of(memberId);

        commandGateway.sendAndWait(
                EnrollmentCommands.DissenrollFrom.of(domainEventId, domainTermId, domainMemberId)
        );
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