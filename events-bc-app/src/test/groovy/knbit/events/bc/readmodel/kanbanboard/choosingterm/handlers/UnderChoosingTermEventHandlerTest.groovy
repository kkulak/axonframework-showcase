package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.builders.TermBuilder
import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.builders.EnrollmentIdentifiedTermBuilder
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

import static knbit.events.bc.readmodel.EventDetailsWrapper.sectionOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull

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
                imageUrl      : urlOrNull(eventDetails.imageUrl()),
                section       : sectionOrNull(eventDetails.section()),
                terms         : [],
                reservations  : []
        ]
    }

    def "should remove event on transition"() {
        given:
        collection << [eventId: eventId.value()]

        when:
        def term = EnrollmentIdentifiedTermBuilder.defaultTerm()
        objectUnderTest.on UnderChoosingTermEventEvents.TransitedToEnrollment.of(eventId, eventDetails, [term])

        then:
        collection.find([eventId: eventId.value()]).toArray() == []
    }
}
