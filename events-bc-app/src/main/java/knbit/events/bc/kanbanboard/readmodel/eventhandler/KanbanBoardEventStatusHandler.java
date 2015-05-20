package knbit.events.bc.kanbanboard.readmodel.eventhandler;

import knbit.events.bc.common.readmodel.EventStatusAware;
import knbit.events.bc.event.domain.valueobjects.events.EventCreated;
import knbit.events.bc.kanbanboard.readmodel.service.EventStateMachine;
import knbit.events.bc.kanbanboard.readmodel.repository.KanbanBoardRepository;
import knbit.events.bc.kanbanboard.readmodel.model.KanbanBoard;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KanbanBoardEventStatusHandler {
    private final KanbanBoardRepository kanbanBoardRepository;

    @Autowired
    public KanbanBoardEventStatusHandler(KanbanBoardRepository kanbanBoardRepository) {
        this.kanbanBoardRepository = kanbanBoardRepository;
    }

    @EventHandler
    private void handle(EventCreated event) {
        kanbanBoardRepository.save(
            new KanbanBoard(
                    event.eventId().value(), event.name().value(),
                    event.eventType(), event.eventFrequency(), event.status(),
                    EventStateMachine.match(event.status())
            )
        );
    }

    @EventHandler
    private void handle(EventStatusAware event) {
        final KanbanBoard kanbanBoardEvent = kanbanBoardRepository.findByEventDomainId(
                event.eventId().toString()
        );
        kanbanBoardEvent.setEventStatus(event.status());
        kanbanBoardEvent.setReachableStatus(
                EventStateMachine.match(event.status())
        );
        kanbanBoardRepository.save(kanbanBoardEvent);
    }

}
