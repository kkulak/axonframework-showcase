package knbit.rsintegration.bc.common

case class Reservation(eventId: String, reservationId: String,
                       start: Long, duration: Long, capacity: Int)

case class Term()

case class AcceptedReservation(eventId: String, reservationId: String, term: Term)

case class RejectedReservation(eventId: String, reservationId: String)
