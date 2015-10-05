package knbit.events.bc.readmodel.kanbanboard.columns

import com.mongodb.DBCollection
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermStatusEvents
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

import static knbit.events.bc.common.readmodel.EventStatus.*

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
                eventDomainId: eventId.value()
        ])
        def entryWithoutMongoId = entry.toMap()
        entryWithoutMongoId.remove '_id'

        entryWithoutMongoId == [
                eventDomainId  : eventId.value(),
                name           : eventDetails.name().value(),
                eventType      : eventDetails.type(),
                eventFrequency : eventDetails.frequency(),
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
                eventDomainId: eventId.value()
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
                eventDomainId: eventId.value()
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
                eventDomainId: eventId.value()
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
                eventDomainId: eventId.value()
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
                eventDomainId: eventId.value()
        ])
        def entryWithoutMongoId = entry.toMap()

        entryWithoutMongoId['eventStatus'] == ENROLLMENT
        entryWithoutMongoId['reachableStatus'] == [ENROLLMENT, READY]
    }

}
