package knbit.events.bc.choosingterm.web;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.choosingterm.web.TermsDTO.TermDTO;
import knbit.events.bc.choosingterm.web.TermsDTO.TermProposalDTO;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.commands.InterestAwareEventCommands;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
                                  @RequestParam("fromState") TransitFrom previousState,
                                  @RequestBody TermsDTO termsDTO) {

        final EventId id = EventId.of(eventId);
        final Collection<TermDTO> terms = termsDTO.getTerms();
        final Collection<TermProposalDTO> termProposals = termsDTO.getTermProposals();

        transitFrom(previousState, id);
        addTerms(terms, id);
        bookRoomsFor(termProposals, id);
    }

    // todo: maybe separate controller?
    @RequestMapping(method = RequestMethod.POST, value = "/{eventId}/terms")
    public void addTerm(@PathVariable("eventId") String eventId,
                        @RequestBody TermDTO termDTO) {

        final EventId id = EventId.of(eventId);
        addTerms(ImmutableList.of(termDTO), id);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{eventId}/terms/{termId}")
    public void removeTerm(@PathVariable("eventId") String eventId,
                           @PathVariable("termId") String termId) {

        final EventId eventDomainId = EventId.of(eventId);
        final TermId termDomainId = TermId.of(termId);
        commandGateway.send(
                CommandFactory.removeTermCommandFrom(eventDomainId, termDomainId)
        );
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{eventId}/reservations")
    public void bookRoom(@PathVariable("eventId") String eventId,
                         @RequestBody TermProposalDTO termProposalDTO) {

        final EventId id = EventId.of(eventId);
        bookRoomsFor(ImmutableList.of(termProposalDTO), id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{eventId}/reservations/{reservationId}")
    public void cancelReservation(@PathVariable("eventId") String eventId,
                         @PathVariable("reservationId") String reservationId) {

        final EventId eventDomainId = EventId.of(eventId);
        final ReservationId reservationDomainId = ReservationId.of(reservationId);
        commandGateway.send(
                CommandFactory.cancelReservationCommandFrom(eventDomainId, reservationDomainId)
        );
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
                BacklogEventCommands.TransitToUnderChoosingTermEventCommand.of(eventId)
        );
    }

    private void transitFromInterestAwareEvent(EventId eventId) {
        commandGateway.sendAndWait(
                InterestAwareEventCommands.TransitToUnderTermChoosingEvent.of(eventId)
        );
    }

    private void addTerms(Collection<TermDTO> terms, EventId eventId) {
        terms.stream()
                .map(term -> CommandFactory.addTermCommandFrom(eventId, term))
                .forEach(commandGateway::sendAndWait);
    }

    private void bookRoomsFor(Collection<TermProposalDTO> termProposals, EventId eventId) {
        termProposals.stream()
                .map(proposal -> CommandFactory.bookRoomCommandFrom(eventId, proposal))
                .forEach(commandGateway::sendAndWait);
    }

    enum TransitFrom {
        BACKLOG, SURVEY_INTEREST
    }
}
