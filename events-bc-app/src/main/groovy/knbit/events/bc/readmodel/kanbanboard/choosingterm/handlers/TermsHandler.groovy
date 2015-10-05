package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents
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
                domainId: event.eventId().value()
        ]
        def termData = termDataFrom event

        termsCollection.update(query, [
                $push: [terms: termData]
        ])
    }

    @EventHandler
    def on(TermEvents.TermRemoved event) {
        def query = [
                domainId: event.eventId().value()
        ]
        def termId = event.termId()

        termsCollection.update(query, [
                $pull: [terms: [termId: termId.value()]]
        ])
    }

    private static def termDataFrom(TermEvents.TermAdded event) {
        def term = event.term()

        [
                termId  : event.termId().value(),
                date    : term.duration().start(),
                duration: term.duration().duration().getStandardMinutes(),
                capacity: term.capacity().value(),
                location: term.location().value()
        ]
    }
}
