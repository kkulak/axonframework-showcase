package knbit.events.bc.choosingterm.web;

import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.ReservationCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.TermCommands;
import knbit.events.bc.common.domain.valueobjects.EventId;
import org.joda.time.Duration;

/**
 * Created by novy on 13.09.15.
 */
class CommandFactory {

    public static TermCommands.AddTerm addTermCommandFrom(EventId eventId, TermsDTO.TermDTO term) {
        return TermCommands.AddTerm.of(
                eventId,
                term.getDate(),
                Duration.standardMinutes(term.getDuration()),
                term.getCapacity(),
                term.getLocation()
        );
    }

    public static ReservationCommands.BookRoom bookRoomCommandFrom(EventId eventId, TermsDTO.TermProposalDTO termProposal) {
        return ReservationCommands.BookRoom.of(
                eventId,
                termProposal.getDate(),
                Duration.standardMinutes(termProposal.getDuration()),
                termProposal.getCapacity()
        );
    }

    public static TermCommands.RemoveTerm removeTermCommandFrom(EventId eventId, TermId termId) {
        return TermCommands.RemoveTerm.of(eventId, termId);
    }

    public static ReservationCommands.CancelReservation cancelReservationCommandFrom(EventId eventId,
                                                                                     ReservationId reservationId) {
        return ReservationCommands.CancelReservation.of(eventId, reservationId);
    }
}
