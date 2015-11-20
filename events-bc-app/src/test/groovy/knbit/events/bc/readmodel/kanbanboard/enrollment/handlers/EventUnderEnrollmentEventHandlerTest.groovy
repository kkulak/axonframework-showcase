package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.builders.TermBuilder
import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.builders.EnrollmentIdentifiedTermBuilder
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.eventready.builders.IdentifiedTermWithAttendeeBuilder
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

import static knbit.events.bc.readmodel.EventDetailsWrapper.sectionOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull

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
        def firstTerm = EnrollmentIdentifiedTermBuilder.defaultTerm()
        def secondTerm = EnrollmentIdentifiedTermBuilder.instance()
                .termId(TermId.of('term-id-2'))
                .build()

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
                imageUrl      : urlOrNull(eventDetails.imageUrl()),
                section       : sectionOrNull(eventDetails.section()),
                terms         : [
                        [
                                termId             : firstTerm.termId().value(),
                                date               : firstTerm.duration().start(),
                                duration           : firstTerm.duration().duration().getStandardMinutes(),
                                limit              : firstTerm.capacity().value(),
                                location           : firstTerm.location().value(),
                                participantsLimit  : firstTerm.participantsLimit().value(),
                                lecturers          : [
                                        [
                                                name: 'John Doe',
                                                id  : 'john-doe'
                                        ]
                                ],
                                participants       : []
                        ],
                        [
                                termId      : secondTerm.termId().value(),
                                date        : secondTerm.duration().start(),
                                duration    : secondTerm.duration().duration().getStandardMinutes(),
                                limit       : secondTerm.capacity().value(),
                                location    : secondTerm.location().value(),
                                participantsLimit  : firstTerm.participantsLimit().value(),
                                lecturers          : [
                                        [
                                                name: 'John Doe',
                                                id  : 'john-doe'
                                        ]
                                ],
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
