package knbit.events.bc.common.viewmodel.allevents.eventhandler

import com.mongodb.DBCollection
import knbit.events.bc.common.readmodel.EventStatus
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 29.06.15.
 */

@Component
class AllEventsEventHandler {

    def DBCollection collection

    @Autowired
    SurveyEventHandler(@Qualifier("all-events") DBCollection collection) {
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
                eventFrequency: eventDetails.frequency(),
                status        : EventStatus.SURVEY_INTEREST
        ])
    }

}
