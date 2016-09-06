package knbit.rsintegration.bc

import com.typesafe.config.ConfigFactory

object Config {
  lazy val conf = ConfigFactory.load
  lazy val ENV = conf.getString("knbit.rsintegration.env")
}
