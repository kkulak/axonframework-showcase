package knbit.events.bc.event.domain;

import knbit.events.bc.common.domain.ICommandHandler;
import knbit.events.bc.event.domain.aggregates.AbstractEvent;
import knbit.events.bc.event.domain.aggregates.EventFactory;
import knbit.events.bc.event.domain.valueobjects.commands.CreateEventCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EventCommandHandler implements ICommandHandler<AbstractEvent> {
    private Repository<AbstractEvent> eventRepository;

    @CommandHandler
    public void handle(CreateEventCommand command) {
        final AbstractEvent event = EventFactory.newEvent(
                command.eventId(), command.name(), command.description(), command.eventType(), command.eventFrequency()
        );
        eventRepository.add(event);
    }

    @Autowired
    public void setRepository(@Qualifier("eventRepository") Repository<AbstractEvent> eventRepository) {
        this.eventRepository = eventRepository;
    }

}
