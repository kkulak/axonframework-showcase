package knbit.events.bc.readmodel

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId

/**
 * Created by novy on 24.10.15.
 */
trait RemoveEventRelatedData {

    def RemoveFrom removeDataBy(EventId eventId) {
        new RemoveFrom(eventId.value())
    }

    def RemoveFrom removeDataBy(ReadyEventId eventId) {
        new RemoveFrom(eventId.value())
    }

    static class RemoveFrom {
        private def String eventId

        RemoveFrom(String eventId) {
            this.eventId = eventId
        }

        def from(DBCollection collection) {
            collection.remove([eventId: eventId])
        }
    }
}