package knbit.events.bc.eventready.domain;

import knbit.events.bc.eventready.domain.aggregates.ReadyEvent;
import knbit.events.bc.eventready.domain.valueobjects.ReadyCommands;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 24.10.15.
 */

@Component
public class ReadyEventCommandHandler {

    private final Repository<ReadyEvent> repository;

    @Autowired
    public ReadyEventCommandHandler(@Qualifier("readyEventRepository") Repository<ReadyEvent> repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(ReadyCommands.Create command) {
        final ReadyEvent readyEvent = new ReadyEvent(
                command.readyEventId(),
                command.correlationId(),
                command.eventDetails(),
                command.attendees()
        );
        repository.add(readyEvent);
    }

    @CommandHandler
    public void handle(ReadyCommands.Cancel command) {
        final ReadyEvent readyEvent = repository.load(command.readyEventId());
        readyEvent.cancel();
    }
}
