package knbit.events.bc.interest.viewmodel.eventmaster.handlers

import com.github.fakemongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DBCollection
import knbit.events.bc.common.domain.enums.EventFrequency
import knbit.events.bc.common.domain.enums.EventType
import knbit.events.bc.common.domain.valueobjects.Description
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.common.domain.valueobjects.Name

import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import spock.lang.Specification

/**
 * Created by novy on 04.06.15.
 */
class CreatingInterestAwareEventEventHandlerTest extends Specification {

    def CreatingInterestAwareEventEventHandler objectUnderTest
    def DBCollection collection

    def EventId eventId
    def EventDetails eventDetails

    void setup() {

        def GMongo gMongo = new GMongo(
                new Fongo("test-fongo").getMongo()
        )
        def db = gMongo.getDB("test-db")
        collection = db.getCollection("test-collection")

        objectUnderTest = new CreatingInterestAwareEventEventHandler(collection)
        eventId = EventId.of("eventId")
        eventDetails = EventDetails.of(
                Name.of("name"),
                Description.of("desc"),
                EventType.WORKSHOP,
                EventFrequency.ONE_OFF
        )
    }

    def "should create database entry containing only event details on InterestAwareEventCreated event"() {

        when:
        objectUnderTest.on InterestAwareEvents.Created.of(eventId, eventDetails)

        then:
        def interestAwareEventViewModel = collection.findOne(
                domainId: eventId.value()
        )

        interestAwareEventViewModel["domainId"] == "eventId"
        interestAwareEventViewModel["name"] == "name"
        interestAwareEventViewModel["description"] == "desc"
        interestAwareEventViewModel["eventType"] == EventType.WORKSHOP
        interestAwareEventViewModel["eventFrequency"] == EventFrequency.ONE_OFF
    }
}
