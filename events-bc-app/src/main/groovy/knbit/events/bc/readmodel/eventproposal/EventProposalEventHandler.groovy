package knbit.events.bc.readmodel.eventproposal

import com.mongodb.DBCollection
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposalEvents
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class EventProposalEventHandler {

    def DBCollection collection

    @Autowired
    EventProposalEventHandler(@Qualifier("proposal-events") DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(EventProposalEvents.EventProposed event) {
        collection.insert([
                domainId        : event.eventProposalId().value(),
                name            : event.name().value(),
                description     : event.description().value(),
                eventType       : event.eventType(),
                eventFrequency  : event.eventFrequency(),
                state           : event.proposalState()
        ])
    }

    @EventHandler
    def on(EventProposalEvents.ProposalAccepted event) {
        collection.update(
                [domainId: event.eventProposalId().value()],
                [$set: [state: event.state()]]
        )
    }

    @EventHandler
    def on(EventProposalEvents.ProposalRejected event) {
        collection.update(
                [domainId: event.eventProposalId().value()],
                [$set: [state: event.state()]]
        )
    }

}
