package knbit.events.bc.readmodel.kanbanboard.columns;

import knbit.events.bc.auth.Authorized;
import knbit.events.bc.auth.Role;
import knbit.events.bc.common.readmodel.EventStatus;
import knbit.events.bc.readmodel.kanbanboard.columns.KanbanBoard;
import knbit.events.bc.readmodel.kanbanboard.columns.repository.KanbanBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/kanbanboard")
public class KanbanBoardController {
    private final KanbanBoardRepository kanbanBoardRepository;

    @Autowired
    public KanbanBoardController(KanbanBoardRepository kanbanBoardRepository) {
        this.kanbanBoardRepository = kanbanBoardRepository;
    }

    @Authorized(Role.ADMIN)
    @RequestMapping(method = RequestMethod.GET)
    public Map<EventStatus, List<KanbanBoard>> kanbanBoard() {
        return StreamSupport
                .stream(kanbanBoardRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(KanbanBoard::getEventStatus));
    }

}