package knbit.events.bc.kanbanboard.readmodel.repository;

import knbit.events.bc.kanbanboard.readmodel.model.KanbanBoard;
import org.springframework.data.repository.CrudRepository;

public interface KanbanBoardRepository
        extends CrudRepository<KanbanBoard, Long> {

    KanbanBoard findByEventDomainId(String eventDomainId);

}
