package knbit.events.bc.readmodel.members.dashboard

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.enums.EventType
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.eventready.builders.EventReadyDetailsBuilder
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.readmodel.DBCollectionAware
import org.joda.time.DateTime
import spock.lang.Specification

import static knbit.events.bc.readmodel.EventDetailsWrapper.sectionOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull

/**
 * Created by novy on 20.10.15.
 */
class DashboardEventHandlerTest extends Specification implements DBCollectionAware {

    def DashboardEventHandler objectUnderTest
    def DBCollection collection

    void setup() {
        collection = testCollection()
        objectUnderTest = new DashboardEventHandler(collection)
    }

    def "it should create new database entry on ready event created"() {
        given:
        def eventId = ReadyEventId.of("eventId")
        def eventDetails = EventReadyDetailsBuilder.defaultEventDetails()
        def attendees = [Attendee.of(MemberId.of("attendee1"))]

        when:
        objectUnderTest.on ReadyEvents.Created.of(
                eventId,
                EventId.of('some correlation id'),
                eventDetails,
                attendees
        )

        then:
        def entry = collection.findOne([eventId: eventId.value()])

        stripMongoIdFrom(entry) == [
                eventId          : eventId.value(),
                name             : eventDetails.name().value(),
                description      : eventDetails.description().value(),
                eventType        : eventDetails.type(),
                imageUrl         : urlOrNull(eventDetails.imageUrl()),
                section          : sectionOrNull(eventDetails.section()),
                start            : eventDetails.duration().start(),
                end              : eventDetails.duration().end(),
                participantsLimit: eventDetails.limit().value(),
                location         : eventDetails.location().value(),
                lecturers        : [
                        [
                                name: 'John Doe',
                                id  : 'john-doe'
                        ]
                ],
                attendees        : ['attendee1']
        ]
    }

    def "should modify db entry on ready event details change"() {
        given:
        def eventId = ReadyEventId.of("eventId")
        collection << [
                eventId          : eventId.value(),
                name             : 'spring boot',
                description      : 'will be awesome',
                eventType        : EventType.WORKSHOP,
                imageUrl         : 'http://example.com/image.jpg',
                section          : null,
                start            : DateTime.now(),
                end              : DateTime.now().plusHours(1),
                participantsLimit: 666,
                location         : '3.27A',
                lecturers        : [
                        [
                                name: 'John Doe',
                                id  : 'john-doe'
                        ]
                ],
                attendees        : ['attendee1']
        ]

        when:
        def newDetails = EventReadyDetailsBuilder.defaultEventDetails()
        objectUnderTest.on(ReadyEvents.DetailsChanged.of(eventId, null, newDetails))

        then:
        def entry = collection.findOne([eventId: eventId.value()])

        stripMongoIdFrom(entry) == [
                eventId          : eventId.value(),
                name             : newDetails.name().value(),
                description      : newDetails.description().value(),
                eventType        : newDetails.type(),
                imageUrl         : urlOrNull(newDetails.imageUrl()),
                section          : sectionOrNull(newDetails.section()),
                start            : newDetails.duration().start(),
                end              : newDetails.duration().end(),
                participantsLimit: newDetails.limit().value(),
                location         : newDetails.location().value(),
                lecturers        : [
                        [
                                name: 'John Doe',
                                id  : 'john-doe'
                        ]
                ],
                attendees        : ['attendee1']
        ]
    }

    def "should remove that entry on cancellation"() {
        given:
        def eventId = ReadyEventId.of("anId")
        collection << [eventId: eventId.value()]

        when:
        objectUnderTest.on ReadyEvents.Cancelled.of(eventId, EventReadyDetailsBuilder.defaultEventDetails(), [])

        then:
        collection.find([eventId: eventId.value()]).toArray() == []
    }
}
