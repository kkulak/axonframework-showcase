package knbit.events.bc.readmodel.eventproposal

import com.mongodb.DBCollection
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

    @RequestMapping
    def allEventProposals() {
        eventsProposalCollection.find().toArray()
    }

    @RequestMapping(value = "/{id}")
    def eventProposal(@PathVariable String id) {
        eventsProposalCollection.findOne([
                domainId: id
        ])
    }

}
