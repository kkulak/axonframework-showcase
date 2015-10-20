package knbit.events.bc.readmodel.members.dashboard

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 21.10.15.
 */
class DashboardQueryTest extends Specification implements DBCollectionAware {

    def DashboardQuery objectUnderTest
    def DBCollection dbCollection

    def EventId eventId
    def MemberId member1
    def MemberId member2

    void setup() {
        dbCollection = testCollection()
        objectUnderTest = new DashboardQuery(dbCollection)

        eventId = EventId.of("eventId")
        member1 = MemberId.of("member1")
        member2 = MemberId.of("member2")
    }

    def "should add 'enrolled' property for each event"() {
        given:
        def events = [
                [
                        eventId  : eventId.value(),
                        attendees: [member2.value()]
                ],
                [
                        eventId  : eventId.value(),
                        attendees: [member1.value(), member2.value()]
                ]
        ]

        dbCollection << events

        when:
        def modifiedEvents = objectUnderTest.allWithPreferencesFor(member1).collect { stripMongoIdFrom(it) }

        then:
        modifiedEvents == [
                [
                        eventId  : eventId.value(),
                        attendees: [member2.value()],
                        enrolled : false
                ],
                [
                        eventId  : eventId.value(),
                        attendees: [member1.value(), member2.value()],
                        enrolled : true
                ]
        ]

    }
}
