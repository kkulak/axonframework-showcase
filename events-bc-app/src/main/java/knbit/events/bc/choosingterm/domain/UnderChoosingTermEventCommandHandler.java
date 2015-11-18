package knbit.events.bc.choosingterm.domain;

import knbit.events.bc.choosingterm.domain.aggregates.UnderChoosingTermEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.*;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.ReservationCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.TermCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 16.08.15.
 */
@Component
public class UnderChoosingTermEventCommandHandler {

    private final Repository<UnderChoosingTermEvent> repository;

    @Autowired
    public UnderChoosingTermEventCommandHandler(
            @Qualifier("underChoosingTermEventRepository") Repository<UnderChoosingTermEvent> repository) {

        this.repository = repository;
    }

    @CommandHandler
    public void handle(UnderChoosingTermEventCommands.Create command) {
        final UnderChoosingTermEvent underChoosingTermEvent = new UnderChoosingTermEvent(
                command.eventId(), command.eventDetails()
        );

        repository.add(underChoosingTermEvent);
    }

    @CommandHandler
    public void handle(TermCommands.AddTerm command) {
        final UnderChoosingTermEvent underChoosingTermEvent =
                repository.load(command.eventId());

        final Term newTerm = Term.of(
                EventDuration.of(command.startDate(), command.duration()),
                Capacity.of(command.capacity()),
                Location.of(command.location())
        );

        underChoosingTermEvent.addTerm(newTerm);
    }

    @CommandHandler
    public void handle(TermCommands.RemoveTerm command) {
        final UnderChoosingTermEvent underChoosingTermEvent =
                repository.load(command.eventId());

        underChoosingTermEvent.removeTerm(command.termId());
    }

    @CommandHandler
    public void handle(ReservationCommands.BookRoom command) {
        final UnderChoosingTermEvent underChoosingTermEvent =
                repository.load(command.eventId());

        final EventDuration eventDuration = EventDuration.of(
                command.startDate(), command.duration()
        );
        final Capacity capacity = Capacity.of(command.capacity());

        underChoosingTermEvent.bookRoomFor(eventDuration, capacity);
    }

    @CommandHandler
    public void handle(ReservationCommands.AcceptReservation command) {
        final UnderChoosingTermEvent underChoosingTermEvent =
                repository.load(command.eventId());

        final ReservationId reservationId = command.reservationId();
        final Location location = Location.of(command.location());

        underChoosingTermEvent.acceptReservationWithLocation(reservationId, location);
    }

    @CommandHandler
    public void handle(ReservationCommands.RejectReservation command) {
        final UnderChoosingTermEvent underChoosingTermEvent =
                repository.load(command.eventId());

        underChoosingTermEvent.rejectReservation(command.reservationId());
    }

    @CommandHandler
    public void handle(ReservationCommands.CancelReservation command) {
        final UnderChoosingTermEvent underChoosingTermEvent =
                repository.load(command.eventId());

        underChoosingTermEvent.cancelReservation(command.reservationId());
    }

    @CommandHandler
    public void handle(ReservationCommands.FailReservation command) {
        final UnderChoosingTermEvent underChoosingTermEvent =
                repository.load(command.eventId());

        underChoosingTermEvent.failReservation(command.reservationId(), command.reason());
    }

    @CommandHandler
    public void handle(UnderChoosingTermEventCommands.TransitToEnrollment command) {
        final UnderChoosingTermEvent underChoosingTermEvent =
                repository.load(command.eventId());

        underChoosingTermEvent.transitToEnrollment(command.termClosures());
    }

}
