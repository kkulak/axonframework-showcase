package knbit.events.bc.kanbanboard.readmodel.web;

import knbit.events.bc.kanbanboard.readmodel.model.KanbanBoard;
import knbit.events.bc.kanbanboard.readmodel.repository.KanbanBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kanbanboard")
public class KanbanBoardController {
    private final KanbanBoardRepository kanbanBoardRepository;

    @Autowired
    public KanbanBoardController(KanbanBoardRepository kanbanBoardRepository) {
        this.kanbanBoardRepository = kanbanBoardRepository;
    }

    @RequestMapping( method = RequestMethod.GET)
    public Iterable<KanbanBoard> kanbanBoard() {
        return kanbanBoardRepository.findAll();
    }

}
