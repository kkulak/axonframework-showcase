package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 13.09.15.
 */

@Component
class UnderChoosingTermEventHandler {

    def DBCollection termsCollection

    @Autowired
    UnderChoosingTermEventHandler(@Qualifier("choosing-term") DBCollection termsCollection) {
        this.termsCollection = termsCollection
    }

    @EventHandler
    def on(UnderChoosingTermEventEvents.Created event) {
        def eventId = event.eventId()
        def eventDetails = event.eventDetails()

        termsCollection.insert([
                domainId      : eventId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                eventFrequency: eventDetails.frequency(),
                terms         : [],
                reservations  : []
        ])
    }
}
