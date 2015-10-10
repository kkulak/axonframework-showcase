package knbit.events.bc.readmodel.members.surveypreview.handlers

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.readmodel.DBCollectionAware
import knbit.events.bc.readmodel.members.surveypreview.InterestMemberViewModelQuery
import knbit.events.bc.readmodel.members.surveypreview.VoteType
import spock.lang.Specification

/**
 * Created by novy on 10.10.15.
 */
class InterestMemberViewModelQueryTest extends Specification implements DBCollectionAware {

    def DBCollection surveyCollection
    def DBCollection votesCollection

    def InterestMemberViewModelQuery objectUnderTest

    def MemberId memberId

    void setup() {
        surveyCollection = testCollectionWithName('survey')
        votesCollection = testCollectionWithName('votes')

        objectUnderTest = new InterestMemberViewModelQuery(surveyCollection, votesCollection)

        memberId = MemberId.of("memberId")
    }

    def "if member voted for given event, it should include vote result in query result"() {
        given:
        def allEvents = [
                [eventId: 'event1', foo: 'foo'],
                [eventId: 'event2', foo: 'bar'],
                [eventId: 'event3', foo: 'baz'],
                [eventId: 'event4', foo: 'foobar']
        ]
        surveyCollection << allEvents

        votesCollection << [
                [eventId: 'event2', memberId: memberId.value(), voted: VoteType.POSITIVE],
                [eventId: 'event4', memberId: memberId.value(), voted: VoteType.NEGATIVE]
        ]

        when:
        def queryResult = objectUnderTest.queryFor(memberId)

        then:
        queryResult.collect { stripMongoIdFrom(it) } == [
                [eventId: 'event1', foo: 'foo'],
                [eventId: 'event2', foo: 'bar', voted: VoteType.POSITIVE],
                [eventId: 'event3', foo: 'baz'],
                [eventId: 'event4', foo: 'foobar', voted: VoteType.NEGATIVE],
        ]
    }
}
