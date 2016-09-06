package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.ReservationId
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents
import knbit.events.bc.common.domain.valueobjects.EventId
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 13.09.15.
 */

@Component
class ReservationsHandler {

    def DBCollection termsCollection

    @Autowired
    ReservationsHandler(@Qualifier("choosing-term") DBCollection termsCollection) {
        this.termsCollection = termsCollection
    }

    @EventHandler
    def on(ReservationEvents.RoomRequested event) {
        def query = [
                eventId: event.eventId().value()
        ]
        def reservationData = reservationDataFrom(event)

        termsCollection.update(query, [
                $push: [reservations: reservationData]
        ])
    }

    @EventHandler
    def on(ReservationEvents.ReservationCancelled event) {
        removeReservationFor(event.eventId(), event.reservationId())
    }

    @EventHandler
    def on(ReservationEvents.ReservationAccepted event) {
        removeReservationFor(event.eventId(), event.reservationId())
    }

    @EventHandler
    def on(ReservationEvents.ReservationRejected event) {
        removeReservationFor(event.eventId(), event.reservationId())
    }

    @EventHandler
    def on(ReservationEvents.ReservationFailed event) {
        removeReservationFor(event.eventId(), event.reservationId())
    }

    private static def reservationDataFrom(ReservationEvents.RoomRequested event) {
        def eventDuration = event.eventDuration()
        [
                reservationId: event.reservationId().value(),
                date         : eventDuration.start(),
                duration     : eventDuration.duration().getStandardMinutes(),
                capacity     : event.capacity().value()
        ]
    }

    private removeReservationFor(EventId eventId, ReservationId reservationId) {
        def query = [
                eventId: eventId.value()
        ]
        termsCollection.update(query, [
                $pull: [
                        reservations: [reservationId: reservationId.value()]
                ]
        ])
    }
}
