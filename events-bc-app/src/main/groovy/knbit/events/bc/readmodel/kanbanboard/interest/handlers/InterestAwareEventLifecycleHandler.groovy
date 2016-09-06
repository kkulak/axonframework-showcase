package knbit.events.bc.readmodel.kanbanboard.interest.handlers

import com.mongodb.DBCollection
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.readmodel.EventDetailsWrapper
import knbit.events.bc.readmodel.RemoveEventRelatedData
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 04.06.15.
 */

@Component
class InterestAwareEventLifecycleHandler implements RemoveEventRelatedData {

    def DBCollection collection

    @Autowired
    InterestAwareEventLifecycleHandler(@Qualifier("survey") DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(InterestAwareEvents.Created event) {
        def eventId = [eventId: event.eventId().value()]
        def eventDetails = EventDetailsWrapper.asMap(event.eventDetails())

        collection.insert(eventId + eventDetails)
    }

    @EventHandler
    def on(InterestAwareEvents.TransitedToUnderChoosingTerm event) {
        removeDataBy(event.eventId()).from(collection)
    }

    // todo: common trait?
    @EventHandler
    def on(InterestAwareEvents.InterestAwareEventCancelled event) {
        removeDataBy(event.eventId()).from(collection)
    }
}
