package knbit.events.bc.interest.viewmodel.eventmaster.handlers

import com.foursquare.fongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy
import knbit.events.bc.interest.domain.valueobjects.events.SurveyVotedDownEvent
import knbit.events.bc.interest.domain.valueobjects.events.SurveyVotedUpEvent
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyingInterestStartedEvent
import spock.lang.Specification

/**
 * Created by novy on 04.06.15.
 */
class SurveyEventHandlerTest extends Specification {

    def SurveyEventHandler objectUnderTest
    def DBCollection collection

    def EventId eventId

    void setup() {

        def GMongo gMongo = new GMongo(
                new Fongo("test-fongo").getMongo()
        )
        def db = gMongo.getDB("test-db")
        collection = db.getCollection("test-collection")

        objectUnderTest = new SurveyEventHandler(collection)
        eventId = EventId.of("eventId")
    }


    def "should set votedUp and votedDown props to 0 on SurveyingInterestStartedEvent"() {

        given:
        collection << [
                domainId: eventId.value()
        ]

        when:
        objectUnderTest.on(
                SurveyingInterestStartedEvent.of(
                        eventId, Mock(InterestPolicy.class)
                )
        )

        def interestAwareEventViewModel = collection.findOne(
                domainId: eventId.value()
        )

        then:
        interestAwareEventViewModel["votedUp"] == 0
        interestAwareEventViewModel["votedDown"] == 0
    }

    def "should increase positive votes on SurveyVotedUpEvent"() {

        given:
        collection << [
                domainId: eventId.value(),
                votedUp : 0
        ]

        when:
        objectUnderTest.on(
                SurveyVotedUpEvent.of(
                        eventId, Attendee.of("fname", "lname")
                )
        )

        def interestAwareEventViewModel = collection.findOne(
                domainId: eventId.value()
        )

        then:
        interestAwareEventViewModel["votedUp"] == 1
    }

    def "should increase negative votes on SurveyVotedDownEvent"() {

        given:
        collection << [
                domainId : eventId.value(),
                votedDown: 0
        ]

        when:
        objectUnderTest.on(
                SurveyVotedDownEvent.of(
                        eventId, Attendee.of("fname", "lname")
                )
        )

        def interestAwareEventViewModel = collection.findOne(
                domainId: eventId.value()
        )

        then:
        interestAwareEventViewModel["votedDown"] == 1
    }
}
