package knbit.rsintegration.bc.scheduling.response

import knbit.rsintegration.bc.common.Term

import scala.util.Random

sealed trait Result
case class Success(term: Term) extends Result
case class Unresolved() extends Result
case class Rejection() extends Result
case class Failure() extends Result

trait ResponseStrategy {
  def checkResponse(requestId: String): Result
}

case class MockResponseStrategy() extends ResponseStrategy {

  override def checkResponse(requestId: String): Result = {
    val res = new Random().nextInt()
    if(res % 3 == 0)
      Success(Term())
    else
      Rejection()
  }

}
