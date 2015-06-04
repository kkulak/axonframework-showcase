package knbit.events.bc.interest.viewmodel.eventmaster.handlers

import com.mongodb.DBCollection
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by novy on 04.06.15.
 */

@Component
class CreatingInterestAwareEventEventHandler {

    def DBCollection collection

    @Autowired
    CreatingInterestAwareEventEventHandler(DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(InterestAwareEventCreated event) {
        def eventId = event.eventId()
        def eventDetails = event.eventDetails()

        collection.insert([
                domainId      : eventId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                eventFrequency: eventDetails.frequency()
        ])
    }
}
