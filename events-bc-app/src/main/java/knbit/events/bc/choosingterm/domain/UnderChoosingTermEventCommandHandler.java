package knbit.events.bc.choosingterm.domain;

import knbit.events.bc.choosingterm.domain.aggregates.UnderChoosingTermEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.CreateUnderChoosingTermEventCommand;
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
}
