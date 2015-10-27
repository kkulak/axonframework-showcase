package knbit.events.bc.readmodel.kanbanboard.interest.handlers

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 04.06.15.
 */
class CreatingInterestAwareEventEventHandlerTest extends Specification implements DBCollectionAware {

    def CreatingInterestAwareEventEventHandler objectUnderTest
    def DBCollection collection

    def EventId eventId
    def EventDetails eventDetails

    void setup() {
        collection = testCollection()
        objectUnderTest = new CreatingInterestAwareEventEventHandler(collection)
        eventId = EventId.of("eventId")
        eventDetails = EventDetailsBuilder.defaultEventDetails()
    }

    def "should create database entry containing only event details on InterestAwareEventCreated event"() {

        when:
        objectUnderTest.on InterestAwareEvents.Created.of(eventId, eventDetails)

        then:
        def interestAwareEventViewModel = collection.findOne(
                eventId: eventId.value()
        )

        interestAwareEventViewModel["eventId"] == "eventId"
        interestAwareEventViewModel["name"] == eventDetails.name().value()
        interestAwareEventViewModel["description"] == eventDetails.description().value()
        interestAwareEventViewModel["eventType"] == eventDetails.type()
    }

    def "should delete entry on event transition"() {
        given:
        collection << [eventId: eventId.value()]

        when:
        objectUnderTest.on InterestAwareEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)

        then:
        collection.find([eventId: eventId.value()]).toArray() == []
    }
}
