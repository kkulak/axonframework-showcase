package knbit.events.bc.eventproposal.infrastructure.dispatcher;

import knbit.events.bc.common.config.AMQPConstants;
import knbit.events.bc.common.dispatcher.MessageDispatcher;
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposalEvents;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 09.05.15.
 */
// TODO: find better place for sth like this
@Component
public class ProposalNotificationEventHandler {
    private final MessageDispatcher dispatcher;
    private static final String NOTIFICATION_TYPE = "EVENT_PROPOSED";

    @Autowired
    public ProposalNotificationEventHandler(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @EventHandler
    public void handle(EventProposalEvents.EventProposed event) {
        final ProposalNotification proposalNotification = new ProposalNotification(
                event.eventProposalId().value(),
                event.name().value(),
                event.description().value(),
                event.eventType(),
                event.eventFrequency()
        );

        dispatcher.dispatch(proposalNotification, AMQPConstants.NOTIFICATION_QUEUE, NOTIFICATION_TYPE);
    }
}
