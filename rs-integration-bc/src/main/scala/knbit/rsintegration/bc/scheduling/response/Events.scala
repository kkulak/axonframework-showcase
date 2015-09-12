package knbit.rsintegration.bc.scheduling.response

import knbit.rsintegration.bc.common.Reservation

case class ResponseInitializedEvent(requestId: String,
                                    reservation: Reservation,
                                    responseStrategy: ResponseStrategy,
                                    schedulingStrategy: SchedulingStrategy)

case object ResponseFinishedEvent

case object UnresolvedResponseEvent

case object FailureResponseEvent