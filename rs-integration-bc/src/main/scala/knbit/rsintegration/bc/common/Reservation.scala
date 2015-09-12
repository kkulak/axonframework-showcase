package knbit.rsintegration.bc.common

case class Reservation(eventId: String, reservationId: String,
                       start: Long, duration: Long, capacity: Int)

case class Term()
