package knbit.events.bc.enrollment.web;

import knbit.events.bc.auth.Authorized;
import knbit.events.bc.auth.Role;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.enrollment.domain.valueobjects.TermClosure;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EnrollmentCommands;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        transitToEnrollment(domainEventId, terms);
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

    private void transitToEnrollment(EventId eventId, Collection<TermDTO> terms) {
        commandGateway.sendAndWait(
                UnderChoosingTermEventCommands.TransitToEnrollment.of(eventId, termClosureOf(terms))
        );
    }

    private Collection<Lecturer> domainLecturersOf(Collection<TermDTO.Lecturer> dtos) {
        return dtos
                .stream()
                .map(dto -> Lecturer.of(dto.getName(), dto.getId().orElse(null)))
                .collect(Collectors.toList());
    }

    private List<TermClosure> termClosureOf(Collection<TermDTO> terms) {
        final Function<TermDTO, TermClosure> mapToClosureFromTermDTO = termDTO -> {
            final TermId termId = TermId.of(termDTO.getTermId());
            final Collection<Lecturer> lecturers = domainLecturersOf(termDTO.getLecturers());
            final ParticipantsLimit participantsLimit = ParticipantsLimit.of(termDTO.getParticipantsLimit());

            return TermClosure.of(termId, lecturers, participantsLimit);
        };

        return terms
                .stream()
                .map(mapToClosureFromTermDTO)
                .collect(Collectors.toList());
    }

}