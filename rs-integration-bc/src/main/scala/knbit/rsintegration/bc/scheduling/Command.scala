package knbit.rsintegration.bc.scheduling

case class ScheduleRoomReservationRequestCommand(eventId: String, reservationId: String,
                                          start: Long, duration: Long, capacity: Int)

case class RequestRoomReservationCommand(requestId: String, eventId: String, reservationId: String,
                                         start: Long, duration: Long, capacity: Int)

case class CancelRoomReservationRequestCommand(requestId: String)

case class ScheduleRoomReservationResponseCommand(eventId: String, reservationId: String,
                                                  start: Long, duration: Long, capacity: Int)

case class ResponseRoomReservationCommand(responseId: String, eventId: String, reservationId: String,
                                          start: Long, duration: Long, capacity: Int)
