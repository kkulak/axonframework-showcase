package knbit.events.bc.kanbanboard.readmodel.service

import spock.lang.Specification

import static knbit.events.bc.common.readmodel.EventStatus.*

class EventStateMachineTest extends Specification {

    def "should return empty list given invalid event status"() {
        given:
        def status = null

        when:
        def reachableStates = EventStateMachine.match(status)

        then:
        reachableStates == []
    }

    def "should return reachable states from Backlog state"() {
        given:
        def status = BACKLOG

        when:
        def reachableStates = EventStateMachine.match(status)

        then:
        reachableStates == [BACKLOG, SURVEYING_INTEREST, CHOOSING_TERM]
    }

    def "should return reachable states from SurveyingInterest state"() {
        given:
        def status = SURVEYING_INTEREST

        when:
        def reachableStates = EventStateMachine.match(status)

        then:
        reachableStates == [SURVEYING_INTEREST, CHOOSING_TERM]
    }

    def "should return reachable states from ChoosingTerm state"() {
        given:
        def status = CHOOSING_TERM

        when:
        def reachableStates = EventStateMachine.match(status)

        then:
        reachableStates == [CHOOSING_TERM, ROOM_BOOKING]
    }

}
