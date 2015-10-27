package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.builders.TermBuilder
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 13.09.15.
 */
class UnderChoosingTermEventHandlerTest extends Specification implements DBCollectionAware {

    def UnderChoosingTermEventHandler objectUnderTest
    def DBCollection collection

    def EventId eventId
    def EventDetails eventDetails

    void setup() {
        collection = testCollection()
        objectUnderTest = new UnderChoosingTermEventHandler(collection)
        eventId = EventId.of("eventId")
        eventDetails = EventDetailsBuilder.defaultEventDetails()
    }

    def "should create new database entry containing event details and no terms nor reservations"() {

        when:
        objectUnderTest.on UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)

        then:
        def choosingTermsPreview = collection.findOne(
                eventId: eventId.value()
        )

        def previewExcludingMongoId = choosingTermsPreview.toMap()
        previewExcludingMongoId.remove "_id"

        previewExcludingMongoId == [
                eventId       : eventId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                terms         : [],
                reservations  : []
        ]
    }

    def "should remove event on transition"() {
        given:
        collection << [eventId: eventId.value()]

        when:
        def term = IdentifiedTerm.of(TermId.of("id"), TermBuilder.defaultTerm())
        objectUnderTest.on UnderChoosingTermEventEvents.TransitedToEnrollment.of(eventId, eventDetails, [term])

        then:
        collection.find([eventId: eventId.value()]).toArray() == []
    }
}
