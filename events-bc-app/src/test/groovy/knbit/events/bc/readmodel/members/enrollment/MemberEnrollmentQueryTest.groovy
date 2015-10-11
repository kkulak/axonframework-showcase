package knbit.events.bc.readmodel.members.enrollment

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 11.10.15.
 */
class MemberEnrollmentQueryTest extends Specification implements DBCollectionAware {

    def DBCollection enrollmentEventsCollection
    def DBCollection enrollmentParticipantsCollection

    def MemberEnrollmentQuery objectUnderTest

    void setup() {
        enrollmentEventsCollection = testCollectionWithName('events')
        enrollmentParticipantsCollection = testCollectionWithName('participants')

        objectUnderTest = new MemberEnrollmentQuery(enrollmentEventsCollection, enrollmentParticipantsCollection)
    }

    def "should create 'chosenTerm' property on event if participant enrolled and create 'enrolled' property on each term"() {
        given:
        def memberId = MemberId.of("memberId")
        enrollmentEventsCollection << [
                [
                        eventId: '#event1',
                        terms  : [
                                [termId: '#event1 #term1'],
                                [termId: '#event1 #term2'],
                        ]
                ],
                [
                        eventId: '#event2',
                        terms  : [[termId: '#event2 #term1']]
                ]
        ]

        enrollmentParticipantsCollection << [
                [eventId: '#event1', memberId: memberId.value(), termId: '#event1 #term2']
        ]

        when:
        def allEvents = objectUnderTest.queryFor(memberId)

        then:
        allEvents.collect { stripMongoIdFrom(it) } == [
                [
                        eventId   : '#event1',
                        chosenTerm: '#event1 #term2',
                        terms     : [
                                [termId: '#event1 #term1', enrolled: false],
                                [termId: '#event1 #term2', enrolled: true],
                        ]
                ],
                [
                        eventId: '#event2',
                        terms  : [[termId: '#event2 #term1', enrolled: false]]
                ]
        ]
    }
}
