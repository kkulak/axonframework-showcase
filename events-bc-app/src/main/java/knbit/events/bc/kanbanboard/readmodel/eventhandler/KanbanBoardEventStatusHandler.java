package knbit.events.bc.kanbanboard.readmodel.eventhandler;

import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventCreated;
import knbit.events.bc.common.readmodel.EventStatusAware;
import knbit.events.bc.kanbanboard.readmodel.model.KanbanBoard;
import knbit.events.bc.kanbanboard.readmodel.repository.KanbanBoardRepository;
import knbit.events.bc.kanbanboard.readmodel.service.EventStateMachine;
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
    private void handle(BacklogEventCreated event) {
        kanbanBoardRepository.save(
            new KanbanBoard(
                    event.eventId().value(), event.eventDetails().name().value(),
                    event.eventDetails().type(), event.eventDetails().frequency(),
                    event.status(), EventStateMachine.match(event.status())
            )
        );
    }

    @EventHandler
    private void handle(EventStatusAware event) {
        final KanbanBoard kanbanBoardEvent = kanbanBoardRepository.findByEventDomainId(
                event.eventId().value()
        );
        kanbanBoardEvent.setEventStatus(event.status());
        kanbanBoardEvent.getReachableStatus().clear();
        kanbanBoardEvent.getReachableStatus()
                .addAll(EventStateMachine.match(event.status()));
        kanbanBoardRepository.save(kanbanBoardEvent);
    }

}
