package knbit.events.bc.domain.proposing.event.readmodel;

import knbit.events.bc.domain.proposing.event.valueobjects.events.EventProposed;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 06.05.15.
 */

@Component
public class EventProposalViewModelEventHandler {

    private final EventProposalReadModelRepository repository;

    @Autowired
    public EventProposalViewModelEventHandler(EventProposalReadModelRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void handle(EventProposed event) {
        repository.save(
                new EventProposalViewModel(
                        event.name().name(),
                        event.description().description(),
                        event.eventType(),
                        event.proposalState()
                )
        );
    }
}
