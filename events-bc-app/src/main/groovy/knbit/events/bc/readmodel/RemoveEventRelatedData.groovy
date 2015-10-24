package knbit.events.bc.readmodel

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.EventId

/**
 * Created by novy on 24.10.15.
 */
trait RemoveEventRelatedData {

    def RemoveFrom removeDataBy(EventId eventId) {
        new RemoveFrom(eventId)
    }

    static class RemoveFrom {
        private def EventId eventId

        RemoveFrom(EventId eventId) {
            this.eventId = eventId
        }

        def from(DBCollection collection) {
            collection.remove([eventId: eventId.value()])
        }
    }
}