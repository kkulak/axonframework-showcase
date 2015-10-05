package knbit.events.bc.readmodel.kanbanboard.interest.handlers

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyStartingEvents
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 04.06.15.
 */
class SurveyEventHandlerTest extends Specification implements DBCollectionAware {

    def SurveyEventHandler objectUnderTest
    def DBCollection collection

    def EventId eventId

    void setup() {
        collection = testCollection()
        objectUnderTest = new SurveyEventHandler(collection)
        eventId = EventId.of("eventId")
    }

    def "should set votedUp and votedDown props to 0 on SurveyingInterestStartedEvent"() {

        given:
        collection << [
                _id     : 'id',
                domainId: eventId.value()
        ]

        when:
        objectUnderTest.on(
                SurveyStartingEvents.Started.of(
                        eventId, Mock(InterestPolicy.class)
                )
        )

        def interestAwareEventViewModel = collection.findOne(
                domainId: eventId.value()
        )

        then:
        interestAwareEventViewModel == [
                _id      : 'id',
                domainId : eventId.value(),
                votedUp  : 0,
                votedDown: 0
        ]
    }

    def "should increase positive votes on SurveyVotedUpEvent"() {

        given:
        collection << [
                _id     : 'id',
                domainId: eventId.value(),
                votedUp : 0
        ]

        when:
        objectUnderTest.on(
                SurveyEvents.VotedUp.of(
                        eventId, Attendee.of("fname", "lname")
                )
        )

        def interestAwareEventViewModel = collection.findOne(
                domainId: eventId.value()
        )

        then:
        interestAwareEventViewModel == [
                _id     : 'id',
                domainId: eventId.value(),
                votedUp : 1
        ]
    }

    def "should increase negative votes on SurveyVotedDownEvent"() {

        given:
        collection << [
                _id      : 'id',
                domainId : eventId.value(),
                votedDown: 0
        ]

        when:
        objectUnderTest.on(
                SurveyEvents.VotedDown.of(
                        eventId, Attendee.of("fname", "lname")
                )
        )

        def interestAwareEventViewModel = collection.findOne(
                domainId: eventId.value()
        )

        then:
        interestAwareEventViewModel == [
                _id      : 'id',
                domainId : eventId.value(),
                votedDown: 1
        ]
    }
}
