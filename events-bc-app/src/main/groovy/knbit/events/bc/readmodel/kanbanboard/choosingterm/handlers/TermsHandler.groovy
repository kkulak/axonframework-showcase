package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents
import knbit.events.bc.readmodel.TermWrapper
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 13.09.15.
 */

@Component
class TermsHandler {

    def DBCollection termsCollection

    @Autowired
    TermsHandler(@Qualifier("choosing-term") DBCollection termsCollection) {
        this.termsCollection = termsCollection
    }

    @EventHandler
    def on(TermEvents.TermAdded event) {
        def query = [
                eventId: event.eventId().value()
        ]
        def termData = TermWrapper.asMap(event.termId(), event.term())

        termsCollection.update(query, [
                $push: [terms: termData]
        ])
    }

    @EventHandler
    def on(TermEvents.TermRemoved event) {
        def query = [
                eventId: event.eventId().value()
        ]
        def termId = event.termId()

        termsCollection.update(query, [
                $pull: [terms: [termId: termId.value()]]
        ])
    }
}
