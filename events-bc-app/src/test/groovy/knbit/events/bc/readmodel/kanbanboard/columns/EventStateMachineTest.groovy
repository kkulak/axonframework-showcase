package knbit.events.bc.readmodel.kanbanboard.columns

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
        reachableStates == [BACKLOG, SURVEY_INTEREST, CHOOSING_TERM]
    }

    def "should return reachable states from SurveyingInterest state"() {
        given:
        def status = SURVEY_INTEREST

        when:
        def reachableStates = EventStateMachine.match(status)

        then:
        reachableStates == [SURVEY_INTEREST, CHOOSING_TERM]
    }

    def "should return reachable states from ChoosingTerm state"() {
        given:
        def status = CHOOSING_TERM

        when:
        def reachableStates = EventStateMachine.match(status)

        then:
        reachableStates == [CHOOSING_TERM, ENROLLMENT]
    }

    def "should return reachable states from Enrollment state"() {
        given:
        def status = ENROLLMENT

        when:
        def reachableStates = EventStateMachine.match(status)

        then:
        reachableStates == [ENROLLMENT, READY]
    }

}
