package knbit.events.bc.readmodel.members.dashboard

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.eventready.builders.EventReadyDetailsBuilder
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

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
                start            : eventDetails.duration().start(),
                end              : eventDetails.duration().end(),
                participantsLimit: eventDetails.limit().value(),
                location         : eventDetails.location().value(),
                lecturer         : [
                        firstName: eventDetails.lecturer().firstName(),
                        lastName : eventDetails.lecturer().lastName()
                ],
                attendees        : ['attendee1']
        ]
    }
}
