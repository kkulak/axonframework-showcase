package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.builders.TermBuilder
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.eventready.builders.IdentifiedTermWithAttendeeBuilder
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 05.10.15.
 */
class EventUnderEnrollmentEventHandlerTest extends Specification implements DBCollectionAware {

    def EventUnderEnrollmentEventHandler objectUnderTest
    def DBCollection collection

    def EventId eventId
    def EventDetails eventDetails

    void setup() {
        collection = testCollection()
        objectUnderTest = new EventUnderEnrollmentEventHandler(collection)
        eventId = EventId.of("eventId")
        eventDetails = EventDetailsBuilder.defaultEventDetails()
    }

    def "should create new database entry containing event details and terms with no participants"() {
        given:
        def firstTerm = IdentifiedTerm.of(
                TermId.of("id1"), TermBuilder.defaultTerm()
        )
        def secondTerm = IdentifiedTerm.of(
                TermId.of("id2"), TermBuilder.defaultTerm()
        )

        when:
        objectUnderTest.on EventUnderEnrollmentEvents.Created.of(
                eventId, eventDetails, [firstTerm, secondTerm]
        )

        then:
        def enrollmentPreview = collection.findOne(
                eventId: eventId.value()
        )

        def withoutMongoDBId = enrollmentPreview.toMap()
        withoutMongoDBId.remove "_id"

        withoutMongoDBId == [
                eventId       : eventId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                eventFrequency: eventDetails.frequency(),
                terms         : [
                        [
                                termId      : firstTerm.termId().value(),
                                date        : firstTerm.duration().start(),
                                duration    : firstTerm.duration().duration().getStandardMinutes(),
                                capacity    : firstTerm.capacity().value(),
                                location    : firstTerm.location().value(),
                                participants: []
                        ],
                        [
                                termId      : secondTerm.termId().value(),
                                date        : secondTerm.duration().start(),
                                duration    : secondTerm.duration().duration().getStandardMinutes(),
                                capacity    : secondTerm.capacity().value(),
                                location    : secondTerm.location().value(),
                                participants: []
                        ]
                ]
        ]
    }

    def "should cleanup database on event transition"() {
        given:
        collection << [eventId: eventId.value()]

        when:
        objectUnderTest.on EventUnderEnrollmentEvents.TransitedToReady.of(
                eventId, eventDetails, [IdentifiedTermWithAttendeeBuilder.defaultTerm()]
        )

        then:
        collection.find([eventId: eventId.value()]).toArray() == []
    }
}
