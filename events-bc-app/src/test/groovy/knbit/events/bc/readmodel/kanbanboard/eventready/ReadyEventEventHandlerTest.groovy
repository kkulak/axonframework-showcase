package knbit.events.bc.readmodel.kanbanboard.eventready

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.eventready.builders.EventReadyDetailsBuilder
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.readmodel.DBCollectionAware
import knbit.events.bc.readmodel.TermWrapper
import knbit.events.bc.readmodel.kanbanboard.common.participantdetails.ParticipantDetailsRepository
import spock.lang.Specification

import static knbit.events.bc.readmodel.EventDetailsWrapper.sectionOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull

class ReadyEventEventHandlerTest extends Specification implements DBCollectionAware {
    def ReadyEventEventHandler objectUnderTest
    def DBCollection collection
    def ParticipantDetailsRepository participantRepository

    def ReadyEventId readyEventId
    def EventId correlationId
    def EventReadyDetails eventDetails
    def Collection<Attendee> attendees

    void setup() {
        collection = testCollection()
        participantRepository = Mock(ParticipantDetailsRepository)
        objectUnderTest = new ReadyEventEventHandler(collection, participantRepository)
        readyEventId = ReadyEventId.of('readyEventId')
        correlationId = EventId.of('eventId')
        eventDetails = EventReadyDetailsBuilder.defaultEventDetails()
        attendees = [Attendee.of(MemberId.of('member-id'))]
    }

    def "should create new database entry containing event details and one participant"() {
        given:
        participantRepository.detailsFor(MemberId.of('member-id')) >> [userId: 'member-id', firstName: 'John', lastName: 'Doe']

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
                        lecturers   : TermWrapper.lecturersOf(eventDetails.lecturers()),
                        participants: [
                                [userId: 'member-id', firstName: 'John', lastName: 'Doe']
                        ]
                ]
        ]
    }

}
