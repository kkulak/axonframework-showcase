package knbit.rsintegration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.reflect.ClassTag

package object utils {

  def parse[T](body: Array[Byte])(implicit m: scala.reflect.Manifest[T]): T = {
    val om = new ObjectMapper()
    om.registerModule(DefaultScalaModule)
    om.readValue(body, m.runtimeClass).asInstanceOf[T]
  }

}
