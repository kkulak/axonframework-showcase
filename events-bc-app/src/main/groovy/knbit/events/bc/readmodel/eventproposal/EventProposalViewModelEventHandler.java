package knbit.events.bc.readmodel.eventproposal;

import knbit.events.bc.eventproposal.domain.valueobjects.events.*;
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
    public void handle(EventProposalEvents.EventProposed event) {
        repository.save(
                new EventProposalViewModel(
                        event.eventProposalId().value(),
                        event.name().value(),
                        event.description().value(),
                        event.eventType(),
                        event.eventFrequency(),
                        event.proposalState()
                )
        );
    }

    @EventHandler
    public void handle(EventProposalEvents.ProposalAccepted event) {
        handleStateChange(event);
    }

    @EventHandler
    public void handle(EventProposalEvents.ProposalRejected event) {
        handleStateChange(event);
    }

    private void handleStateChange(EventProposalEvents.ProposalStateChanged event) {
        final EventProposalViewModel viewModel = repository.findByDomainId(
                event.eventProposalId().value()
        );
        viewModel.setState(event.state());
        repository.save(viewModel);
    }
}
