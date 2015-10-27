package knbit.events.bc.readmodel.eventproposal

import com.mongodb.DBCollection

import knbit.events.bc.common.domain.enums.EventType
import knbit.events.bc.common.domain.valueobjects.Description
import knbit.events.bc.common.domain.valueobjects.Name
import knbit.events.bc.eventproposal.domain.enums.ProposalState
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposalEvents
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

class EventProposalHandlerTest extends Specification implements DBCollectionAware {
    def EventProposalEventHandler objectUnderTest
    def DBCollection collection

    def proposalId = EventProposalId.of("proposal-id")
    def name = Name.of("name")
    def description = Description.of("description")
    def eventType = EventType.LECTURE
    def state = ProposalState.PENDING

    def setup() {
        collection = testCollection()
        objectUnderTest = new EventProposalEventHandler(collection)
    }

    def cleanup() {
        collection.drop()
    }

    def "should create new db entry containing event proposal"() {
        when:
        objectUnderTest.on(new EventProposalEvents.EventProposed(
                proposalId, name, description, eventType, state
        ))

        then:
        def entry = collection.findOne([
                domainId: proposalId.value()
        ])
        def entryWithoutMongoId = entry.toMap()
        entryWithoutMongoId.remove '_id'

        entryWithoutMongoId == [
                domainId      : proposalId.value(),
                name          : name.value(),
                description   : description.value(),
                eventType     : eventType,
                state         : state
        ]
    }

    def "should update event proposal state on accepted proposal event"() {
        given:
        objectUnderTest.on(new EventProposalEvents.EventProposed(
                proposalId, name, description, eventType, state
        ))

        when:
        objectUnderTest.on(new EventProposalEvents.ProposalAccepted(
                proposalId, eventType, ProposalState.ACCEPTED
        ))

        then:
        def entry = collection.findOne([
                domainId: proposalId.value()
        ])
        def entryWithoutMongoId = entry.toMap()

        entryWithoutMongoId['state'] == ProposalState.ACCEPTED
    }

    def "should update event proposal state on rejected proposal event"() {
        given:
        objectUnderTest.on(new EventProposalEvents.EventProposed(
                proposalId, name, description, eventType, state
        ))

        when:
        objectUnderTest.on(new EventProposalEvents.ProposalRejected(
                proposalId, ProposalState.REJECTED
        ))

        then:
        def entry = collection.findOne([
                domainId: proposalId.value()
        ])
        def entryWithoutMongoId = entry.toMap()

        entryWithoutMongoId['state'] == ProposalState.REJECTED
    }

}
