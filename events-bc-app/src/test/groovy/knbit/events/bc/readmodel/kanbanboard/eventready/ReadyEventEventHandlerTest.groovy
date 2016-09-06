package knbit.events.bc.readmodel.kanbanboard.eventready

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.enums.EventType
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
import org.joda.time.DateTime
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
                eventId: readyEventId.value()
        )

        def withoutMongoDBId = readyEventPreview.toMap()
        withoutMongoDBId.remove "_id"

        withoutMongoDBId == [
                eventId      : readyEventId.value(),
                correlationId: correlationId.value(),
                name         : eventDetails.name().value(),
                description  : eventDetails.description().value(),
                eventType    : eventDetails.type(),
                imageUrl     : urlOrNull(eventDetails.imageUrl()),
                section      : sectionOrNull(eventDetails.section()),
                term         : [
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

    def "should update details if needed"() {
        given:
        collection << [
                eventId      : readyEventId.value(),
                correlationId: correlationId.value(),
                name         : 'just a name',
                description  : 'just a description',
                eventType    : EventType.LECTURE,
                imageUrl     : 'http://example.com/example.jpg',
                section      : null,
                term         : [
                        date        : DateTime.now(),
                        duration    : 60,
                        limit       : 333,
                        location    : '3.21A',
                        lecturers   : [
                                name: 'Kamil Kulak',
                                id  : 'kulakkam'
                        ],
                        participants: [
                                [userId: 'member-id', firstName: 'John', lastName: 'Doe']
                        ]
                ]
        ]

        when:
        def newDetails = EventReadyDetailsBuilder.defaultEventDetails()

        objectUnderTest.on(ReadyEvents.DetailsChanged.of(readyEventId, null, newDetails))

        then:
        def readyEventPreview = collection.findOne([eventId: readyEventId.value()])

        stripMongoIdFrom(readyEventPreview) == [
                eventId      : readyEventId.value(),
                correlationId: correlationId.value(),
                name         : newDetails.name().value(),
                description  : newDetails.description().value(),
                eventType    : newDetails.type(),
                imageUrl     : urlOrNull(newDetails.imageUrl()),
                section      : sectionOrNull(newDetails.section()),
                term         : [
                        date        : newDetails.duration().start(),
                        duration    : newDetails.duration().duration().getStandardMinutes(),
                        limit       : newDetails.limit().value(),
                        location    : newDetails.location().value(),
                        lecturers   : TermWrapper.lecturersOf(newDetails.lecturers()),
                        participants: [
                                [userId: 'member-id', firstName: 'John', lastName: 'Doe']
                        ]
                ]
        ]
    }

    def "should remove db entry on cancellation"() {
        given:
        collection << [
                eventId      : readyEventId.value(),
                correlationId: correlationId.value()
        ]

        when:
        objectUnderTest.on ReadyEvents.Cancelled.of(readyEventId, EventReadyDetailsBuilder.defaultEventDetails(), [])

        then:
        collection.find([eventId: readyEventId.value()]).toArray() == []
    }
}
