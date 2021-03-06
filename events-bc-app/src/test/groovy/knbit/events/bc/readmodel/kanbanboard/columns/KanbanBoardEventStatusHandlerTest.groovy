package knbit.events.bc.readmodel.kanbanboard.columns

import com.mongodb.DBCollection
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents
import knbit.events.bc.choosingterm.domain.valuobjects.Location
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermStatusEvents
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.common.domain.valueobjects.EventCancelled
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.common.domain.valueobjects.Name
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.eventready.builders.EventReadyDetailsBuilder
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.readmodel.DBCollectionAware
import knbit.events.bc.readmodel.EventDetailsWrapper
import knbit.events.bc.readmodel.TermWrapper
import spock.lang.Specification

import static knbit.events.bc.common.readmodel.EventStatus.*
import static knbit.events.bc.readmodel.EventDetailsWrapper.sectionOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull

class KanbanBoardEventStatusHandlerTest extends Specification implements DBCollectionAware {
    def KanbanBoardEventStatusHandler objectUnderTest
    def DBCollection collection

    def eventId = EventId.of("event-id")
    def eventDetails = EventDetailsBuilder.defaultEventDetails()

    def setup() {
        collection = testCollection()
        objectUnderTest = new KanbanBoardEventStatusHandler(collection)
    }

    def cleanup() {
        collection.drop()
    }

    def "should create new db entry containing event in backlog state"() {
        when:
        objectUnderTest.on(BacklogEventEvents.Created.of(eventId, eventDetails))

        then:
        def entry = collection.findOne([
                eventId: eventId.value()
        ])

        stripMongoIdFrom(entry) == [
                eventId        : eventId.value(),
                name           : eventDetails.name().value(),
                eventType      : eventDetails.type(),
                imageUrl       : urlOrNull(eventDetails.imageUrl()),
                section        : sectionOrNull(eventDetails.section()),
                eventStatus    : BACKLOG,
                reachableStatus: [BACKLOG, SURVEY_INTEREST, CHOOSING_TERM]
        ]
    }

    def "should update db entry if details changed on backlog event"() {
        given:
        collection << [
                eventId        : eventId.value(),
                name           : eventDetails.name().value(),
                eventType      : eventDetails.type(),
                imageUrl       : urlOrNull(eventDetails.imageUrl()),
                section        : sectionOrNull(eventDetails.section()),
                eventStatus    : BACKLOG,
                reachableStatus: [BACKLOG, SURVEY_INTEREST, CHOOSING_TERM]
        ]

        when:
        def newDetails = EventDetailsBuilder.instance().name(Name.of("differentName")).build()
        objectUnderTest.on(BacklogEventEvents.EventDetailsChanged.of(eventId, eventDetails, newDetails))

        then:
        def entry = collection.findOne([
                eventId: eventId.value()
        ])

        stripMongoIdFrom(entry) == [
                eventId        : eventId.value(),
                name           : newDetails.name().value(),
                eventType      : newDetails.type(),
                imageUrl       : urlOrNull(newDetails.imageUrl()),
                section        : sectionOrNull(newDetails.section()),
                eventStatus    : BACKLOG,
                reachableStatus: [BACKLOG, SURVEY_INTEREST, CHOOSING_TERM]
        ]
    }

    def "should set appropriate event states on interest aware event created"() {
        given:
        objectUnderTest.on(BacklogEventEvents.Created.of(eventId, eventDetails))

        when:
        objectUnderTest.on(InterestAwareEvents.Created.of(eventId, eventDetails))

        then:
        def entry = collection.findOne([
                eventId: eventId.value()
        ])
        def entryWithoutMongoId = entry.toMap()

        entryWithoutMongoId['eventStatus'] == SURVEY_INTEREST
        entryWithoutMongoId['reachableStatus'] == [SURVEY_INTEREST, CHOOSING_TERM]
    }

    def "should set appropriate event states on choosing term event created"() {
        given:
        objectUnderTest.on(BacklogEventEvents.Created.of(eventId, eventDetails))

        when:
        objectUnderTest.on(UnderChoosingTermEventEvents.Created.of(eventId, eventDetails))

        then:
        def entry = collection.findOne([
                eventId: eventId.value()
        ])
        def entryWithoutMongoId = entry.toMap()

        entryWithoutMongoId['eventStatus'] == CHOOSING_TERM
        entryWithoutMongoId['reachableStatus'] == [CHOOSING_TERM]
    }

    def "should set appropriate event states on terms ready event"() {
        given:
        objectUnderTest.on(BacklogEventEvents.Created.of(eventId, eventDetails))

        when:
        objectUnderTest.on(TermStatusEvents.Ready.of(eventId))

        then:
        def entry = collection.findOne([
                eventId: eventId.value()
        ])
        def entryWithoutMongoId = entry.toMap()

        entryWithoutMongoId['eventStatus'] == CHOOSING_TERM
        entryWithoutMongoId['reachableStatus'] == [CHOOSING_TERM, ENROLLMENT]
    }

    def "should set appropriate event states on terms pending event"() {
        given:
        objectUnderTest.on(BacklogEventEvents.Created.of(eventId, eventDetails))

        when:
        objectUnderTest.on(TermStatusEvents.Pending.of(eventId))

        then:
        def entry = collection.findOne([
                eventId: eventId.value()
        ])
        def entryWithoutMongoId = entry.toMap()

        entryWithoutMongoId['eventStatus'] == CHOOSING_TERM
        entryWithoutMongoId['reachableStatus'] == [CHOOSING_TERM]
    }

    def "should set appropriate event states on enrollment event created"() {
        given:
        objectUnderTest.on(BacklogEventEvents.Created.of(eventId, eventDetails))

        when:
        objectUnderTest.on(EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, []))

        then:
        def entry = collection.findOne([
                eventId: eventId.value()
        ])
        def entryWithoutMongoId = entry.toMap()

        entryWithoutMongoId['eventStatus'] == ENROLLMENT
        entryWithoutMongoId['reachableStatus'] == [ENROLLMENT, READY]
    }

    def "should remove previous entry on transition to ready"() {
        given:
        objectUnderTest.on(BacklogEventEvents.Created.of(eventId, eventDetails))

        when:
        objectUnderTest.on(EventUnderEnrollmentEvents.TransitedToReady.of(eventId, eventDetails, []))

        then:
        !collection.findOne(eventId: eventId.value())
    }

    def "should add new entry with appropriate event states on ready event created"() {
        given:
        objectUnderTest.on(BacklogEventEvents.Created.of(eventId, eventDetails))

        def eventReadyDetails = EventReadyDetailsBuilder
                .instance()
                .eventDetails(eventDetails)
                .build()
        def readyEventId = ReadyEventId.of("readyEventId")

        when:
        objectUnderTest.on(ReadyEvents.Created.of(readyEventId, eventId, eventReadyDetails, []))

        then:
        def entry = collection.findOne([eventId: readyEventId.value()])

        stripMongoIdFrom(entry) == [
                eventId        : readyEventId.value(),
                name           : eventReadyDetails.name().value(),
                eventType      : eventReadyDetails.type(),
                imageUrl       : urlOrNull(eventReadyDetails.imageUrl()),
                section        : sectionOrNull(eventReadyDetails.section()),
                start          : eventReadyDetails.duration().start(),
                location       : eventReadyDetails.location().value(),
                lecturers      : TermWrapper.lecturersOf(eventReadyDetails.lecturers()),
                eventStatus    : READY,
                reachableStatus: [READY]
        ]
    }

    def "should update collection on ready event details change"() {
        given:
        def oldDetails = EventReadyDetailsBuilder.defaultEventDetails()
        def readyEventId = ReadyEventId.of("readyEventId")

        collection << [
                eventId        : readyEventId.value(),
                name           : oldDetails.name().value(),
                eventType      : oldDetails.type(),
                imageUrl       : urlOrNull(oldDetails.imageUrl()),
                section        : sectionOrNull(oldDetails.section()),
                start          : oldDetails.duration().start(),
                location       : oldDetails.location().value(),
                lecturers      : TermWrapper.lecturersOf(oldDetails.lecturers()),
                eventStatus    : READY,
                reachableStatus: [READY]
        ]

        when:
        def newDetails =
                EventReadyDetailsBuilder.instance().location(Location.of("totally different location")).build()

        objectUnderTest.on(ReadyEvents.DetailsChanged.of(readyEventId, oldDetails, newDetails))

        then:
        def entry = collection.findOne([eventId: readyEventId.value()])

        stripMongoIdFrom(entry) == [
                eventId        : readyEventId.value(),
                name           : newDetails.name().value(),
                eventType      : newDetails.type(),
                imageUrl       : urlOrNull(newDetails.imageUrl()),
                section        : sectionOrNull(newDetails.section()),
                start          : newDetails.duration().start(),
                location       : newDetails.location().value(),
                lecturers      : TermWrapper.lecturersOf(newDetails.lecturers()),
                eventStatus    : READY,
                reachableStatus: [READY]
        ]
    }

    def "should remove db entry on event took place"() {
        given:
        def readyEventId = ReadyEventId.of("readyEventId")
        def eventReadyDetails = EventReadyDetailsBuilder.defaultEventDetails()
        def attendees = [Attendee.of(MemberId.of("member"))]

        objectUnderTest.on(ReadyEvents.Created.of(readyEventId, eventId, eventReadyDetails, attendees))

        when:
        objectUnderTest.on(ReadyEvents.TookPlace.of(readyEventId, eventReadyDetails, attendees))

        then:
        !collection.findOne([eventId: readyEventId.value()])
    }

    def "should remove db entry on event cancelled"() {
        given:
        def eventId = EventId.of("eventId")

        collection << [eventId: eventId.value()]

        when:
        objectUnderTest.on([eventId: { return eventId }] as EventCancelled)

        then:
        !collection.findOne([eventId: eventId.value()])
    }
}
