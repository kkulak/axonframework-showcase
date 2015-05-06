package knbit.events.bc.eventproposal.readmodel;

import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposed;
import knbit.events.bc.eventproposal.domain.valueobjects.events.ProposalAcceptedEvent;
import knbit.events.bc.eventproposal.domain.valueobjects.events.ProposalRejectedEvent;
import knbit.events.bc.eventproposal.domain.valueobjects.events.ProposalStateChanged;
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

    @EventHandler
    public void handle(ProposalAcceptedEvent event) {
        handleStateChange(event);
    }

    @EventHandler
    public void handle(ProposalRejectedEvent event) {
        handleStateChange(event);
    }

    private void handleStateChange(ProposalStateChanged event) {
        final EventProposalViewModel viewModel = repository.findByDomainId(
                event.eventProposalId().value()
        );
        viewModel.setState(event.state());
    }
}
