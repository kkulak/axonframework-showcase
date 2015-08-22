package knbit.events.bc.choosingterm.web;

import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.ReservationCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.TermCommands;
import knbit.events.bc.choosingterm.web.TermsDTO.TermDTO;
import knbit.events.bc.choosingterm.web.TermsDTO.TermProposalDTO;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.commands.InterestAwareEventCommands;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.joda.time.Duration;
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
                .map(term -> addTermCommandFrom(eventId, term))
                .forEach(commandGateway::sendAndWait);
    }

    private TermCommands.AddTerm addTermCommandFrom(EventId eventId, TermDTO term) {
        return TermCommands.AddTerm.of(
                eventId,
                term.getDate(),
                Duration.standardMinutes(term.getDuration()),
                term.getCapacity(),
                term.getLocation()
        );
    }

    private void bookRoomsFor(Collection<TermProposalDTO> termProposals, EventId eventId) {
        termProposals.stream()
                .map(proposal -> bookRoomCommandFrom(eventId, proposal))
                .forEach(commandGateway::sendAndWait);

    }

    private ReservationCommands.BookRoom bookRoomCommandFrom(EventId eventId, TermProposalDTO termProposal) {
        return ReservationCommands.BookRoom.of(
                eventId,
                termProposal.getDate(),
                Duration.standardMinutes(termProposal.getDuration()),
                termProposal.getCapacity()
        );
    }

    enum TransitFrom {
        BACKLOG, SURVEY_INTEREST
    }
}
