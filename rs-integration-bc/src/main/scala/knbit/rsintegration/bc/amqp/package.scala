package knbit.rsintegration.bc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

package object amqp {
  val om = new ObjectMapper()
  om.registerModule(DefaultScalaModule)

  def parse[T](body: Array[Byte])(implicit m: scala.reflect.Manifest[T]): T = {
    om.readValue(body, m.runtimeClass).asInstanceOf[T]
  }

  def bytes(body: AnyRef): Array[Byte] = {
    om.writeValueAsBytes(body)
  }

}
