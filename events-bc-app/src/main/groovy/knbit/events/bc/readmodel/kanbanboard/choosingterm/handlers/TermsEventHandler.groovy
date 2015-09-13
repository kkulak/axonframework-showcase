package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.Term
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 13.09.15.
 */

@Component
class TermsEventHandler {

    def DBCollection termsCollection

    @Autowired
    TermsEventHandler(@Qualifier("choosing-term") DBCollection termsCollection) {
        this.termsCollection = termsCollection
    }

    @EventHandler
    def on(TermEvents.TermAdded event) {
        def query = [
                domainId: event.eventId().value()
        ]
        def term = event.term()
        def termData = termDataFrom term

        termsCollection.update(query, [
                $push: [terms: termData]
        ])
    }

    @EventHandler
    def on(TermEvents.TermRemoved event) {
        def query = [
                domainId: event.eventId().value()
        ]
        def term = event.term()
        def termData = termDataFrom term

        termsCollection.update(query, [
                $pull: [terms: termData]
        ])
    }

    private static def termDataFrom(Term term) {
        [
                date    : term.duration().start(),
                duration: term.duration().duration().getStandardMinutes(),
                capacity: term.capacity().value(),
                location: term.location().value()
        ]
    }
}
