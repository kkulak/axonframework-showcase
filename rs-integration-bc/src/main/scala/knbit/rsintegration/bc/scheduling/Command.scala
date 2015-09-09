package knbit.rsintegration.bc.scheduling

import knbit.rsintegration.bc.common.Reservation

case class ScheduleRoomReservationRequestCommand(reservation: Reservation)

case class RequestRoomReservationCommand(requestId: String, reservation: Reservation)

case class CancelRoomReservationRequestCommand(requestId: String)

case class ScheduleRoomReservationResponseCommand(reservation: Reservation)

case class ResponseRoomReservationCommand(responseId: String, reservation: Reservation)
