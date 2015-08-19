package knbit.events.bc.choosingterm.domain;

import knbit.events.bc.choosingterm.domain.aggregates.UnderChoosingTermEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.AddTermCommand;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.BookRoomCommand;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.CreateUnderChoosingTermEventCommand;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.RemoveTermCommand;
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
    public void handle(CreateUnderChoosingTermEventCommand command) {
        final UnderChoosingTermEvent underChoosingTermEvent = new UnderChoosingTermEvent(
                command.eventId(), command.eventDetails()
        );

        repository.add(underChoosingTermEvent);
    }

    @CommandHandler
    public void handle(AddTermCommand command) {
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
    public void handle(RemoveTermCommand command) {
        final UnderChoosingTermEvent underChoosingTermEvent =
                repository.load(command.eventId());

        final Term termToRemove = Term.of(
                EventDuration.of(command.startDate(), command.duration()),
                Capacity.of(command.capacity()),
                Location.of(command.location())
        );

        underChoosingTermEvent.removeTerm(termToRemove);
    }

    @CommandHandler
    public void handle(BookRoomCommand command) {
        final UnderChoosingTermEvent underChoosingTermEvent =
                repository.load(command.eventId());

        final EventDuration eventDuration = EventDuration.of(
                command.startDate(), command.duration()
        );
        final Capacity capacity = Capacity.of(command.capacity());

        underChoosingTermEvent.bookRoomFor(eventDuration, capacity);
    }
}
