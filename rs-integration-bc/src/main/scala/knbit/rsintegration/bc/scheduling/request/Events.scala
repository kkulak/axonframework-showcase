package knbit.rsintegration.bc.scheduling.request

import knbit.rsintegration.bc.common.Reservation

case class RequestInitializedEvent(reservation: Reservation,
                                   requestStrategy: RequestStrategy,
                                   schedulingStrategy: RequestSchedulingStrategy)

case object RequestFinishedEvent

case object RequestFailedEvent

case class RequestSucceededEvent(requestId: String, reservation: Reservation)