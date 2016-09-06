package knbit.events.bc.eventready.web;

import knbit.events.bc.auth.Authorized;
import knbit.events.bc.auth.Role;
import knbit.events.bc.backlogevent.web.EventDetailsDTOTransformer;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;
import knbit.events.bc.eventready.domain.valueobjects.ReadyCommands;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by novy on 24.10.15.
 */

@RestController
@RequestMapping(value = "/events")
public class ReadyEventController {

    private final CommandGateway commandGateway;
    private final EventDetailsDTOTransformer eventDetailsDTOTransformer;

    @Autowired
    public ReadyEventController(CommandGateway commandGateway, EventDetailsDTOTransformer eventDetailsDTOTransformer) {
        this.commandGateway = commandGateway;
        this.eventDetailsDTOTransformer = eventDetailsDTOTransformer;
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
    public void changeDetails(@PathVariable("eventId") String eventId,
                              @RequestBody @Valid EventReadyDetailsDTO newDetailsDTO) {

        final ReadyEventId domainEventId = ReadyEventId.of(eventId);
        final EventReadyDetails newDetails = fromDto(newDetailsDTO);

        commandGateway.send(ReadyCommands.ChangeDetails.of(domainEventId, newDetails));
    }

    private EventReadyDetails fromDto(EventReadyDetailsDTO newDetailsDTO) {
        final EventDetails eventDetails =
                eventDetailsDTOTransformer.eventDetailsFrom(newDetailsDTO.getEventBacklogDTO());

        return EventReadyDetails.of(
                eventDetails,
                EventDuration.of(newDetailsDTO.getStart(), newDetailsDTO.getEnd()),
                ParticipantsLimit.of(newDetailsDTO.getParticipantsLimit()),
                Location.of(newDetailsDTO.getLocation()),
                lecturersFromDTOs(newDetailsDTO.getLecturers())
        );
    }

    private Collection<Lecturer> lecturersFromDTOs(Collection<EventReadyDetailsDTO.LecturerDTO> lecturerDTOs) {
        return lecturerDTOs
                .stream()
                .map(dto -> Lecturer.of(dto.getName(), dto.getId().orElse(null)))
                .collect(Collectors.toList());
    }
}
