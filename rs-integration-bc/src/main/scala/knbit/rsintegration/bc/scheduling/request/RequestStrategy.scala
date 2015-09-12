package knbit.rsintegration.bc.scheduling.request

import knbit.rsintegration.bc.common.Reservation

import scala.util.Random

sealed trait Result
case class Success(requestId: String) extends Result
case class Failure() extends Result

trait RequestStrategy {
  def makeRequest(reservation: Reservation): Result
}

case class MockRequestStrategy() extends RequestStrategy {

  override def makeRequest(reservation: Reservation): Result = {
    val random = new Random().nextInt(5)
    if(random == 2)
      Success("request-id")
    else
      Failure()
  }

}