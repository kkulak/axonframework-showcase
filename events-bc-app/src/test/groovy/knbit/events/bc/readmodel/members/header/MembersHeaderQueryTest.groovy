package knbit.events.bc.readmodel.members.header

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.readmodel.DBCollectionAware
import org.joda.time.DateTime
import spock.lang.Specification

/**
 * Created by novy on 25.10.15.
 */
class MembersHeaderQueryTest extends Specification implements DBCollectionAware {

    def DBCollection survey
    def DBCollection dashboard

    def MembersHeaderQuery objectUnderTest

    void setup() {
        survey = testCollectionWithName("survey")
        dashboard = testCollectionWithName("dashboard")

        objectUnderTest = new MembersHeaderQuery(survey, dashboard)
    }

    def "should include number of events under surveying"() {
        when:
        survey << [
                [eventId: 'id1'], [eventId: 'id2'], [eventId: 'id3']
        ]

        then:
        def queryResult = objectUnderTest.newestHeaderData(MemberId.of("id"))
        queryResult.surveyInProgress == 3
    }

    def "should include number of upcoming events, with respect to given date"() {
        when:
        dashboard << [
                [
                        eventId: 'event1',
                        start  : new DateTime(2005, 4, 2, 21, 36)
                ],
                [
                        eventId: 'event1',
                        start  : new DateTime(2005, 4, 2, 21, 37)
                ],
                [
                        eventId: 'event1',
                        start  : new DateTime(2005, 4, 2, 21, 38)
                ]
        ]

        then:
        def queryResult = objectUnderTest.headerDataFor(
                MemberId.of("id"),
                new DateTime(2005, 4, 2, 21, 37)
        )
        queryResult.upcomingEvents == 2
    }

    def "should include nearest event for given memberId and date"() {
        when:
        dashboard << [
                [
                        eventId  : 'event1',
                        start    : new DateTime(2002, 1, 1, 21, 37),
                        attendees: ['member1', 'member2']
                ],
                [
                        eventId  : 'event2',
                        start    : new DateTime(2005, 4, 2, 21, 38),
                        attendees: ['member2', 'member3']
                ],
                [
                        eventId  : 'event3',
                        start    : new DateTime(2010, 4, 3, 21, 37),
                        attendees: ['member2', 'member1']
                ],
                [
                        eventId  : 'event4',
                        start    : new DateTime(2005, 4, 3, 21, 37),
                        attendees: ['member2', 'member1']
                ]
        ]

        then:
        def queryResult = objectUnderTest.headerDataFor(
                MemberId.of("member1"),
                new DateTime(2005, 4, 2, 21, 37)
        )

        stripMongoIdFrom(queryResult.nextEvent) == [
                eventId  : 'event4',
                start    : new DateTime(2005, 4, 3, 21, 37),
                attendees: ['member2', 'member1']
        ]
    }

    def "should return an empty object if there's no matching event by memberId"() {
        when:
        dashboard << [
                [
                        eventId  : 'event1',
                        start    : new DateTime(2002, 1, 1, 21, 37),
                        attendees: ['member1', 'member2']
                ],
                [
                        eventId  : 'event2',
                        start    : new DateTime(2005, 4, 2, 21, 38),
                        attendees: ['member2', 'member3']
                ]
        ]

        then:
        def queryResult = objectUnderTest.headerDataFor(
                MemberId.of("member4"),
                new DateTime(2005, 4, 2, 21, 37)
        )

        !queryResult.nextEvent
    }

    def "should return an empty object if there's no matching event by date"() {
        when:
        dashboard << [
                [
                        eventId  : 'event1',
                        start    : new DateTime(2005, 4, 2, 21, 37),
                        attendees: ['member1']
                ],
                [
                        eventId  : 'event2',
                        start    : new DateTime(2005, 4, 2, 21, 38),
                        attendees: ['member1']
                ]
        ]

        then:
        def queryResult = objectUnderTest.headerDataFor(
                MemberId.of("member1"),
                new DateTime(2005, 4, 2, 21, 39)
        )

        !queryResult.nextEvent
    }
}
