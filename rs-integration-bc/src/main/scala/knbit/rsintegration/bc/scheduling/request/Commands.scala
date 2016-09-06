package knbit.rsintegration.bc.scheduling.request

import knbit.rsintegration.bc.common.Reservation

case class InitializeRequestCommand(reservation: Reservation,
                                    requestStrategy: RequestStrategy,
                                    schedulingStrategy: RequestSchedulingStrategy)

case object SendRequestCommand