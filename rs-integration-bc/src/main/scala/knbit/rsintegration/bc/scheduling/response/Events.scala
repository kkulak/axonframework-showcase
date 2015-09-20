package knbit.rsintegration.bc.scheduling.response

import knbit.rsintegration.bc.common.{Term, Reservation}
import knbit.rsintegration.bc.scheduling.CrossActorEvent

case class ResponseInitializedEvent(requestId: String,
                                    reservation: Reservation,
                                    responseStrategy: ResponseStrategy,
                                    schedulingStrategy: ResponseSchedulingStrategy)

case object ResponseFinishedEvent

case object ResponseTerminatedEvent

case object UnresolvedResponseEvent

case object FailureReservationEvent

case object RejectedResponseEvent

case class SuccessReservationEvent(eventId: String, reservationId: String, term: Term) extends CrossActorEvent

case class RejectedReservationEvent(eventId: String, reservationId: String) extends CrossActorEvent

case class ResponseExceedMaxAttemptAmountEvent(eventId: String, reservationId: String) extends CrossActorEvent