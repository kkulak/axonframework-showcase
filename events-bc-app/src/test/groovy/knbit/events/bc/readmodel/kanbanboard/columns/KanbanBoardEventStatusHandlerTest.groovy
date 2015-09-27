package knbit.events.bc.readmodel.kanbanboard.columns

import com.github.fakemongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DBCollection
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.common.domain.enums.EventFrequency
import knbit.events.bc.common.domain.enums.EventType
import knbit.events.bc.common.domain.valueobjects.Description
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.common.domain.valueobjects.Name
import knbit.events.bc.common.readmodel.EventStatus
import knbit.events.bc.eventproposal.domain.enums.ProposalState
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import spock.lang.Specification

import static knbit.events.bc.common.readmodel.EventStatus.BACKLOG
import static knbit.events.bc.common.readmodel.EventStatus.CHOOSING_TERM
import static knbit.events.bc.common.readmodel.EventStatus.SURVEY_INTEREST

class KanbanBoardEventStatusHandlerTest extends Specification {
    def KanbanBoardEventStatusHandler objectUnderTest
    def DBCollection collection

    def eventId = EventId.of("event-id")
    def name = Name.of("name")
    def desc = Description.of("desc")
    def type = EventType.LECTURE
    def freq = EventFrequency.ONE_OFF
    def eventDetails = EventDetails.of(
            name, desc, type, freq
    )

    def setup() {
        def GMongo gMongo = new GMongo(
                new Fongo("test-fongo").getMongo()
        )
        def db = gMongo.getDB("test-db")
        collection = db.getCollection("test-collection")

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
                eventDomainId   : eventId.value(),
                name            : name.value(),
                eventType       : type,
                eventFrequency  : freq,
                eventStatus     : BACKLOG,
                reachableStatus : [BACKLOG, SURVEY_INTEREST, CHOOSING_TERM]
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

}
