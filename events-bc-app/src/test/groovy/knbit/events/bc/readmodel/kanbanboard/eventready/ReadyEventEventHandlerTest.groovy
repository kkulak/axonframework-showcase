package knbit.events.bc.readmodel.kanbanboard.eventready

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.builders.TermBuilder
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.eventready.builders.EventReadyDetailsBuilder
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.readmodel.DBCollectionAware
import knbit.events.bc.readmodel.kanbanboard.enrollment.handlers.EventUnderEnrollmentEventHandler
import spock.lang.Specification

import static knbit.events.bc.readmodel.EventDetailsWrapper.sectionOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull

class ReadyEventEventHandlerTest extends Specification implements DBCollectionAware {
    def ReadyEventEventHandler objectUnderTest
    def DBCollection collection

    def ReadyEventId readyEventId
    def EventId correlationId
    def EventReadyDetails eventDetails
    def Collection<Attendee> attendees

    void setup() {
        collection = testCollection()
        objectUnderTest = new ReadyEventEventHandler(collection)
        readyEventId = ReadyEventId.of('readyEventId')
        correlationId = EventId.of('eventId')
        eventDetails = EventReadyDetailsBuilder.defaultEventDetails()
        attendees = [Attendee.of(MemberId.of('member-id'))]
    }

    def "should create new database entry containing event details and one participant"() {
        when:
        objectUnderTest.on ReadyEvents.Created.of(
                readyEventId, correlationId, eventDetails, attendees
        )

        then:
        def readyEventPreview = collection.findOne(
                readyEventId: readyEventId.value()
        )

        def withoutMongoDBId = readyEventPreview.toMap()
        withoutMongoDBId.remove "_id"

        withoutMongoDBId == [
                readyEventId  : readyEventId.value(),
                correlationId : correlationId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                imageUrl      : urlOrNull(eventDetails.imageUrl()),
                section       : sectionOrNull(eventDetails.section()),
                term          : [
                        date        : eventDetails.duration().start(),
                        duration    : eventDetails.duration().duration().getStandardMinutes(),
                        limit       : eventDetails.limit().value(),
                        location    : eventDetails.location().value(),
                        participants: []
                ]
        ]
    }

}
