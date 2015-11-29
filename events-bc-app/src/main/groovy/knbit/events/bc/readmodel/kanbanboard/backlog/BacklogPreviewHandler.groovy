package knbit.events.bc.readmodel.kanbanboard.backlog

import com.mongodb.DBCollection
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitionEvents
import knbit.events.bc.readmodel.EventDetailsWrapper
import knbit.events.bc.readmodel.RemoveEventRelatedData
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 28.11.15.
 */

@Component
class BacklogPreviewHandler implements RemoveEventRelatedData {

    def DBCollection backlogCollection

    @Autowired
    BacklogPreviewHandler(@Qualifier("backlogReadmodel") DBCollection backlogCollection) {
        this.backlogCollection = backlogCollection
    }

    @EventHandler
    def on(BacklogEventEvents.Created event) {
        def eventId = [eventId: event.eventId().value()]
        def eventDetails = EventDetailsWrapper.asMap(event.eventDetails())

        backlogCollection << [eventId + eventDetails]
    }

    @EventHandler
    def on(BacklogEventEvents.EventDetailsChanged event) {
        def eventId = event.eventId()
        def newDetailsAsMap = EventDetailsWrapper.asMap(event.newDetails())

        backlogCollection.update([eventId: eventId.value()], [$set: newDetailsAsMap])
    }

    @EventHandler
    def on(BacklogEventTransitionEvents.TransitedToAnotherState event) {
        removeDataBy(event.eventId()).from(backlogCollection)
    }

    @EventHandler
    def on(BacklogEventEvents.Cancelled event) {
        removeDataBy(event.eventId()).from(backlogCollection)
    }
}
