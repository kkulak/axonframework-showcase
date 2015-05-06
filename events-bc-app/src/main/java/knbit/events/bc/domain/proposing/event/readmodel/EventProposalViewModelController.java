package knbit.events.bc.domain.proposing.event.readmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by novy on 06.05.15.
 */

@RestController
@RequestMapping("/proposal")
public class EventProposalViewModelController {

    private final EventProposalReadModelRepository repository;

    @Autowired
    public EventProposalViewModelController(EventProposalReadModelRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<EventProposalViewModel> eventProposals() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EventProposalViewModel eventProposalOf(@PathVariable Long id) {
        return repository.findOne(id);
    }

}
