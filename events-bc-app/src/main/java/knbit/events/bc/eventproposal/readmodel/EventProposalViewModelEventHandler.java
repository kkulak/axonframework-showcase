package knbit.events.bc.eventproposal.readmodel;

import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposed;
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
                        event.eventProposalId().value(),
                        event.name().value(),
                        event.description().value(),
                        event.eventType(),
                        event.proposalState()
                )
        );
    }
}
