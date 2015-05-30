package knbit.events.bc.backlogevent.domain;

import knbit.events.bc.backlogevent.domain.aggregates.BacklogEvent;
import knbit.events.bc.backlogevent.domain.aggregates.EventFactory;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.CreateBacklogEventCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EventCommandHandler {
    private final Repository<BacklogEvent> eventRepository;

    @Autowired
    public EventCommandHandler(@Qualifier("backlogEventRepositories") Repository<BacklogEvent> eventRepository) {
        this.eventRepository = eventRepository;
    }

    @CommandHandler
    public void handle(CreateBacklogEventCommand command) {
        final BacklogEvent event = EventFactory.newEvent(
                command.eventId(), command.eventDetails()
        );
        eventRepository.add(event);
    }

}
