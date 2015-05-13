package knbit.events.bc.eventproposal.notificationdispatcher;

import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposed;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 09.05.15.
 */

@Component
public class ProposalNotificationEventHandler {

    private final ProposalNotificationDispatcher dispatcher;

    @Autowired
    public ProposalNotificationEventHandler(ProposalNotificationDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @EventHandler
    public void handle(EventProposed event) {
        final ProposalNotification proposalNotification = new ProposalNotification(
                event.eventProposalId().value(),
                event.name().value(),
                event.description().value(),
                event.eventType(),
                event.eventFrequency()
        );

        dispatcher.dispatch(proposalNotification);
    }
}
