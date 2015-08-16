package knbit.events.bc.backlogevent.domain;

import knbit.events.bc.backlogevent.domain.aggregates.BacklogEvent;
import knbit.events.bc.backlogevent.domain.aggregates.EventFactory;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.CreateBacklogEventCommand;
import knbit.events.bc.backlogevent.domain.valueobjects.commands.TransitBacklogEventToSurveyInterestAwareEventCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BacklogEventCommandHandler {
    private final Repository<BacklogEvent> backlogEventRepository;

    @Autowired
    public BacklogEventCommandHandler(@Qualifier("backlogEventRepository") Repository<BacklogEvent> backlogEventRepository) {
        this.backlogEventRepository = backlogEventRepository;
    }

    @CommandHandler
    public void handle(CreateBacklogEventCommand command) {
        final BacklogEvent event = EventFactory.newEvent(
                command.eventId(), command.eventDetails()
        );
        backlogEventRepository.add(event);
    }

    @CommandHandler
    public void handle(TransitBacklogEventToSurveyInterestAwareEventCommand command) {
        final BacklogEvent backlogEvent = backlogEventRepository.load(command.eventId());
        backlogEvent.transitToSurveyInterestAwareEvent();
    }

}
