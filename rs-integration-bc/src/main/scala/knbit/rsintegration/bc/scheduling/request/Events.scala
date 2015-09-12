package knbit.rsintegration.bc.scheduling.request

import knbit.rsintegration.bc.common.Reservation

case class RequestInitializedEvent(reservation: Reservation,
                                   requestStrategy: RequestStrategy,
                                   schedulingStrategy: SchedulingStrategy)

case object RequestFailedEvent

case class RequestSucceededEvent(requestId: String)

case class RequestFinishedEvent(requestId: String, reservation: Reservation)