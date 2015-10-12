package knbit.events.bc.readmodel.members.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.builders.TermBuilder
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 11.10.15.
 */
class OnEnrollmentCreationEventHandlerTest extends Specification implements DBCollectionAware {

    def OnEnrollmentCreationHandler objectUnderTest
    def DBCollection collection

    def EventId eventId
    def EventDetails eventDetails

    void setup() {
        collection = testCollection()
        objectUnderTest = new OnEnrollmentCreationHandler(collection)
        eventId = EventId.of("eventId")
        eventDetails = EventDetailsBuilder.defaultEventDetails()
    }

    def "should create new database entry containing event details and terms with zero participant count"() {
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
        def enrollmentPreview = collection.findOne([eventId: eventId.value()])

        stripMongoIdFrom(enrollmentPreview) == [
                eventId       : eventId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                eventFrequency: eventDetails.frequency(),
                terms         : [
                        [
                                termId              : firstTerm.termId().value(),
                                date                : firstTerm.duration().start(),
                                duration            : firstTerm.duration().duration().getStandardMinutes(),
                                capacity            : firstTerm.capacity().value(),
                                location            : firstTerm.location().value(),
                                participantsEnrolled: 0
                        ],
                        [
                                termId              : secondTerm.termId().value(),
                                date                : secondTerm.duration().start(),
                                duration            : secondTerm.duration().duration().getStandardMinutes(),
                                capacity            : secondTerm.capacity().value(),
                                location            : secondTerm.location().value(),
                                participantsEnrolled: 0
                        ]
                ]
        ]
    }
}
