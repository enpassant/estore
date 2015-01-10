package backend

import akka.actor._
import com.typesafe.config.ConfigFactory
import backend.factorial._
import scala.collection.JavaConversions._

/**
 * Booting a cluster backend node with all actors
 */
object Backend extends App {

  // Simple cli parsing
  val port = args match {
    case Array()     => "0"
    case Array(port) => port
    case args        => throw new IllegalArgumentException(s"only ports. Args [ $args ] are invalid")
  }

  val strArgs = args mkString " "
  println(s"Args: $strArgs")
  println(s"Port: $port")

  // System initialization
  val properties = Map(
      "akka.remote.netty.tcp.port" -> port
  )

  val system = ActorSystem("application", (ConfigFactory parseMap properties)
    .withFallback(ConfigFactory.load())
  )

  // Deploy actors and services
  FactorialBackend startOn system
}
