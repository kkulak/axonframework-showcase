package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.github.fakemongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.common.domain.enums.EventFrequency
import knbit.events.bc.common.domain.enums.EventType
import knbit.events.bc.common.domain.valueobjects.Description
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.common.domain.valueobjects.Name
import spock.lang.Specification

/**
 * Created by novy on 13.09.15.
 */
class UnderChoosingTermEventHandlerTest extends Specification {

    def UnderChoosingTermEventHandler objectUnderTest
    def DBCollection collection

    def EventId eventId
    def EventDetails eventDetails

    void setup() {

        def GMongo gMongo = new GMongo(
                new Fongo("test-fongo").getMongo()
        )
        def db = gMongo.getDB("test-db")
        collection = db.getCollection("test-collection")

        objectUnderTest = new UnderChoosingTermEventHandler(collection)
        eventId = EventId.of("eventId")
        eventDetails = EventDetails.of(
                Name.of("name"),
                Description.of("desc"),
                EventType.WORKSHOP,
                EventFrequency.ONE_OFF
        )
    }

    def "should create new database entry containing event details and no terms nor reservations"() {

        when:
        objectUnderTest.on UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)

        then:
        def choosingTermsPreview = collection.findOne(
                domainId: eventId.value()
        )

        def previewExcludingMongoId = choosingTermsPreview.toMap()
        previewExcludingMongoId.remove "_id"

        previewExcludingMongoId == [
                domainId      : eventId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                eventFrequency: eventDetails.frequency(),
                terms         : [],
                reservations  : []
        ]
    }

}
