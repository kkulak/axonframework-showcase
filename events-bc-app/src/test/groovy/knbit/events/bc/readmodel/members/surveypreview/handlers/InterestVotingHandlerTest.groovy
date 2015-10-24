package knbit.events.bc.readmodel.members.surveypreview.handlers

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents
import knbit.events.bc.readmodel.DBCollectionAware
import knbit.events.bc.readmodel.members.surveypreview.VoteType
import spock.lang.Specification

/**
 * Created by novy on 10.10.15.
 */
class InterestVotingHandlerTest extends Specification implements DBCollectionAware {

    def DBCollection eventsCollection
    def DBCollection votesCollection
    def InterestVotingHandler objectUnderTest

    def EventId eventId
    def MemberId memberId

    void setup() {
        eventsCollection = testCollectionWithName('events')
        votesCollection = testCollectionWithName('votes')
        objectUnderTest = new InterestVotingHandler(votesCollection, eventsCollection)

        eventId = EventId.of("eventId")
        memberId = MemberId.of("memberId")
    }

    def "should increase positive vote count on positive vote"() {
        given:
        eventsCollection << [eventId: eventId.value(), votedUp: 0]

        when:
        objectUnderTest.on SurveyEvents.VotedUp.of(eventId, Attendee.of(memberId))

        then:
        def updatedEvent = eventsCollection.findOne([eventId: eventId.value()])
        updatedEvent.votedUp == 1
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

    def "should remove all db entry for given event on that event transition"() {
        given:
        votesCollection << [
                [
                        eventId : eventId.value(),
                        memberId: 'member',
                        voted   : VoteType.NEGATIVE
                ],
                [
                        eventId : eventId.value(),
                        memberId: 'another member',
                        voted   : VoteType.NEGATIVE
                ]
        ]

        when:
        objectUnderTest.on InterestAwareEvents.TransitedToUnderChoosingTerm.of(
                eventId,
                EventDetailsBuilder.defaultEventDetails()
        )

        then:
        votesCollection.find([eventId: eventId.value()]).toArray() == []

    }

    private def collectionEntryFor(EventId eventId, MemberId memberId) {
        stripMongoIdFrom(votesCollection.findOne([
                eventId: eventId.value(), memberId: memberId.value()
        ]))
    }
}
