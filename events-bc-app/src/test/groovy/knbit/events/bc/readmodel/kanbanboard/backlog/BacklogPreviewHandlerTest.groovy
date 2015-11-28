package knbit.events.bc.readmodel.kanbanboard.backlog

import com.mongodb.DBCollection
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitionEvents
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.common.domain.valueobjects.Name
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

import static knbit.events.bc.readmodel.EventDetailsWrapper.sectionOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull

/**
 * Created by novy on 28.11.15.
 */
class BacklogPreviewHandlerTest extends Specification implements DBCollectionAware {

    def BacklogPreviewHandler objectUnderTest
    def DBCollection collection

    def EventId eventId
    def EventDetails eventDetails

    void setup() {
        collection = testCollection()
        objectUnderTest = new BacklogPreviewHandler(collection)
        eventId = EventId.of("eventId")
        eventDetails = EventDetailsBuilder.defaultEventDetails()
    }

    def "should create new database entry containing event details"() {
        when:
        objectUnderTest.on BacklogEventEvents.Created.of(eventId, eventDetails)

        then:
        def backlogPreview = collection.findOne(
                eventId: eventId.value()
        )

        stripMongoIdFrom(backlogPreview) == [
                eventId    : eventId.value(),
                name       : eventDetails.name().value(),
                description: eventDetails.description().value(),
                eventType  : eventDetails.type(),
                imageUrl   : urlOrNull(eventDetails.imageUrl()),
                section    : sectionOrNull(eventDetails.section()),
        ]
    }

    def "should replace details when event details changed"() {
        given:
        collection << [
                eventId    : eventId.value(),
                name       : eventDetails.name().value(),
                description: eventDetails.description().value(),
                eventType  : eventDetails.type(),
                imageUrl   : urlOrNull(eventDetails.imageUrl()),
                section    : sectionOrNull(eventDetails.section()),
        ]

        when:
        def newDetails = EventDetailsBuilder
                .instance()
                .name(Name.of("totally different name"))
                .build()

        objectUnderTest.on BacklogEventEvents.EventDetailsChanged.of(eventId, eventDetails, newDetails)

        println collection.findOne(
                eventId: eventId.value()
        )

        then:
        def backlogPreview = collection.findOne(
                eventId: eventId.value()
        )

        stripMongoIdFrom(backlogPreview) == [
                eventId    : eventId.value(),
                name       : newDetails.name().value(),
                description: newDetails.description().value(),
                eventType  : newDetails.type(),
                imageUrl   : urlOrNull(newDetails.imageUrl()),
                section    : sectionOrNull(newDetails.section()),
        ]
    }

    def "should remove entry on cancellation"() {
        given:
        collection << [eventId: eventId.value()]

        when:
        objectUnderTest.on BacklogEventEvents.Cancelled.of(eventId)

        then:
        collection.find([eventId: eventId.value()]).toArray() == []
    }

    def "should remove entry on transition"() {
        given:
        collection << [eventId: eventId.value()]

        when:
        objectUnderTest.on({
            eventId:
            eventId
        } as BacklogEventTransitionEvents.TransitedToAnotherState)

        then:
        collection.find([eventId: eventId.value()]).toArray() == []
    }
}
