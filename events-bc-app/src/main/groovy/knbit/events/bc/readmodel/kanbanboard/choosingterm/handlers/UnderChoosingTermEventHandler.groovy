package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.readmodel.EventDetailsWrapper
import knbit.events.bc.readmodel.RemoveEventRelatedData
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 13.09.15.
 */

@Component
class UnderChoosingTermEventHandler implements RemoveEventRelatedData {

    def DBCollection termsCollection

    @Autowired
    UnderChoosingTermEventHandler(@Qualifier("choosing-term") DBCollection termsCollection) {
        this.termsCollection = termsCollection
    }

    @EventHandler
    def on(UnderChoosingTermEventEvents.Created event) {
        def eventId = [eventId: event.eventId().value()]
        def eventDetails = EventDetailsWrapper.asMap(event.eventDetails())
        def termsAndReservations = [terms: [], reservations: []]

        termsCollection.insert(eventId + eventDetails + termsAndReservations)
    }

    @EventHandler
    def on(UnderChoosingTermEventEvents.TransitedToEnrollment event) {
        removeDataBy(event.eventId()).from(termsCollection)
    }
}
