package knbit.events.bc.readmodel.members.dashboard

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration
import knbit.events.bc.choosingterm.domain.valuobjects.Location
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.readmodel.DBCollectionAware
import org.joda.time.DateTime
import org.joda.time.Duration
import spock.lang.Specification

/**
 * Created by novy on 20.10.15.
 */
class DashboardEventHandlerTest extends Specification implements DBCollectionAware {

    def DashboardEventHandler objectUnderTest
    def DBCollection collection

    def EventId eventId
    def EventDetails eventDetails

    void setup() {
        collection = testCollection()
        objectUnderTest = new DashboardEventHandler(collection)
        eventId = EventId.of("eventId")
        eventDetails = EventDetailsBuilder.defaultEventDetails()
    }

    def "it should create denormalized datbase entry for each term"() {
        given:
        def firstTerm = IdentifiedTermWithAttendees.of(
                TermId.of("term1"),
                EventDuration.of(DateTime.now(), Duration.standardMinutes(90)),
                ParticipantsLimit.of(666),
                Location.of("3.21A"),
                Lecturer.of("Adam", "Gajek"),
                [Attendee.of(MemberId.of("attendee1"))]
        )

        def secondTerm = IdentifiedTermWithAttendees.of(
                TermId.of("term2"),
                EventDuration.of(DateTime.now(), Duration.standardMinutes(30)),
                ParticipantsLimit.of(333),
                Location.of("3.21B"),
                Lecturer.of("Adam", "Zima"),
                [Attendee.of(MemberId.of("attendee1"))]
        )

        when:
        objectUnderTest.on ReadyEvents.Created.of(eventId, eventDetails, [firstTerm, secondTerm])

        then:
        collection.find([eventId: eventId.value()]).collect { stripMongoIdFrom(it) } == [
                [
                        eventId          : eventId.value(),
                        name             : eventDetails.name().value(),
                        description      : eventDetails.description().value(),
                        eventType        : eventDetails.type(),
                        termId           : firstTerm.termId().value(),
                        start            : firstTerm.duration().start(),
                        end              : firstTerm.duration().end(),
                        participantsLimit: firstTerm.limit().value(),
                        location         : firstTerm.location().value(),
                        lecturer         : [
                                firstName: firstTerm.lecturer().firstName(),
                                lastName : firstTerm.lecturer().lastName()
                        ],
                        attendees        : ['attendee1']
                ],
                [
                        eventId          : eventId.value(),
                        name             : eventDetails.name().value(),
                        description      : eventDetails.description().value(),
                        eventType        : eventDetails.type(),
                        termId           : secondTerm.termId().value(),
                        start            : secondTerm.duration().start(),
                        end              : secondTerm.duration().end(),
                        participantsLimit: secondTerm.limit().value(),
                        location         : secondTerm.location().value(),
                        lecturer         : [
                                firstName: secondTerm.lecturer().firstName(),
                                lastName : secondTerm.lecturer().lastName()
                        ],
                        attendees        : ['attendee1']
                ]
        ]
    }
}
