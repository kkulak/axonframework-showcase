package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.github.fakemongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents
import knbit.events.bc.common.domain.valueobjects.EventId
import org.joda.time.DateTime
import org.joda.time.Duration
import spock.lang.Specification

/**
 * Created by novy on 13.09.15.
 */
class ReservationsHandlerTest extends Specification {

    def ReservationsHandler objectUnderTest
    def DBCollection collection

    def EventId eventId
    def ReservationId reservationId

    void setup() {

        def GMongo gMongo = new GMongo(
                new Fongo("test-fongo").getMongo()
        )
        def db = gMongo.getDB("test-db")
        collection = db.getCollection("test-collection")

        objectUnderTest = new ReservationsHandler(collection)
        eventId = EventId.of("eventId")
        reservationId = ReservationId.of("reservationId")
    }

    def "should create new reservation on corresponding event"() {
        given:
        collection << [
                domainId    : eventId.value(),
                reservations: []
        ]
        def eventDuration = EventDuration.of(
                DateTime.now(), Duration.standardMinutes(90)
        )
        def capacity = Capacity.of(150)

        when:
        objectUnderTest.on(
                ReservationEvents.RoomRequested.of(eventId, reservationId, eventDuration, capacity)
        )

        then:
        def choosingTermsPreview = collection.findOne(
                domainId: eventId.value()
        )

        choosingTermsPreview.reservations == [
                [
                        reservationId: reservationId.value(),
                        date         : eventDuration.start(),
                        duration     : 90,
                        capacity     : 150
                ]
        ]
    }

    def "should remove reservation readmodel on cancelled reservation"() {
        given:
        collection << [
                domainId    : eventId.value(),
                reservations: [
                        [reservationId: reservationId.value()]
                ]
        ]

        when:
        objectUnderTest.on(
                ReservationEvents.ReservationCancelled.of(eventId, reservationId)
        )

        then:
        def choosingTermsPreview = collection.findOne(
                domainId: eventId.value()
        )

        choosingTermsPreview.reservations == []
    }

    def "should remove reservation readmodel on accepted reservation"() {
        given:
        collection << [
                domainId    : eventId.value(),
                reservations: [
                        [reservationId: reservationId.value()]
                ]
        ]

        when:
        objectUnderTest.on(
                ReservationEvents.ReservationAccepted.of(eventId, reservationId)
        )

        then:
        def choosingTermsPreview = collection.findOne(
                domainId: eventId.value()
        )

        choosingTermsPreview.reservations == []
    }

    def "should remove reservation readmodel on rejected reservation"() {
        given:
        collection << [
                domainId    : eventId.value(),
                reservations: [
                        [reservationId: reservationId.value()]
                ]
        ]

        when:
        objectUnderTest.on(
                ReservationEvents.ReservationRejected.of(eventId, reservationId)
        )

        then:
        def choosingTermsPreview = collection.findOne(
                domainId: eventId.value()
        )

        choosingTermsPreview.reservations == []
    }
}