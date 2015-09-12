package knbit.events.bc.readmodel.kanbanboard.columns.repository;

import knbit.events.bc.readmodel.kanbanboard.columns.KanbanBoard;
import org.springframework.data.repository.CrudRepository;

public interface KanbanBoardRepository
        extends CrudRepository<KanbanBoard, Long> {

    KanbanBoard findByEventDomainId(String eventDomainId);

}
