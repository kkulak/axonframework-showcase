package knbit.events.bc.readmodel.allevents

import com.mongodb.DBCollection
import knbit.events.bc.common.readmodel.EventStatus
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.readmodel.EventDetailsWrapper
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import static knbit.events.bc.readmodel.EventDetailsWrapper.sectionOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull

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
    def on(InterestAwareEvents.Created event) {
        def eventId = event.eventId()
        def eventDetails = event.eventDetails()

        collection.insert([
                domainId      : eventId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                imageUrl      : urlOrNull(eventDetails.imageUrl()),
                section       : sectionOrNull(eventDetails.section()),
                status        : EventStatus.SURVEY_INTEREST
        ])
    }

}
