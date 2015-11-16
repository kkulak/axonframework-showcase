package knbit.events.bc.readmodel.eventproposal

import com.mongodb.DBCollection
import knbit.events.bc.auth.Authorized
import knbit.events.bc.auth.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/proposal')
class EventProposalController {
    def eventsProposalCollection

    @Autowired
    EventProposalController(@Qualifier('proposal-events') DBCollection eventsProposalCollection) {
        this.eventsProposalCollection = eventsProposalCollection
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping
    def allEventProposals() {
        eventsProposalCollection.find().toArray()
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(value = "/{id}")
    def eventProposal(@PathVariable String id) {
        eventsProposalCollection.findOne([
                domainId: id
        ])
    }

}
