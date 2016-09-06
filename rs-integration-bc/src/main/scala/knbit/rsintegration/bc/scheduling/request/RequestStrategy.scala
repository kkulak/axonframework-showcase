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
    val list = List(Success("request-id"), Failure())
    val res = new Random().nextInt(2)
    list(res)
  }

}