package knbit.events.bc.readmodel.members.surveypreview.handlers

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents
import knbit.events.bc.readmodel.DBCollectionAware
import knbit.events.bc.readmodel.members.surveypreview.VoteType
import spock.lang.Specification

/**
 * Created by novy on 10.10.15.
 */
class InterestVotingHandlerTest extends Specification implements DBCollectionAware {

    def DBCollection collection
    def InterestVotingHandler objectUnderTest

    def EventId eventId
    def MemberId memberId

    void setup() {
        collection = testCollection()
        objectUnderTest = new InterestVotingHandler(collection)

        eventId = EventId.of("eventId")
        memberId = MemberId.of("memberId")
    }

    def "should update collection on positive vote"() {
        when:
        objectUnderTest.on SurveyEvents.VotedUp.of(eventId, Attendee.of(memberId))

        then:
        collectionEntryFor(eventId, memberId) == [
                eventId : eventId.value(),
                memberId: memberId.value(),
                voted   : VoteType.POSITIVE
        ]
    }

    def "should update collection on negative vote"() {
        when:
        objectUnderTest.on SurveyEvents.VotedDown.of(eventId, Attendee.of(memberId))

        then:
        collectionEntryFor(eventId, memberId) == [
                eventId : eventId.value(),
                memberId: memberId.value(),
                voted   : VoteType.NEGATIVE
        ]
    }

    private def collectionEntryFor(EventId eventId, MemberId memberId) {
        stripMongoIdFrom(collection.findOne([
                eventId: eventId.value(), memberId: memberId.value()
        ]))
    }
}
