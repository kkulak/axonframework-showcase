package knbit.rsintegration.bc.scheduling.request

import knbit.rsintegration.bc.common.Reservation
import knbit.rsintegration.bc.scheduling.SchedulingStrategy

case class InitializeRequestCommand(reservation: Reservation,
                                    requestStrategy: RequestStrategy,
                                    schedulingStrategy: SchedulingStrategy)

case class SendRequestCommand()