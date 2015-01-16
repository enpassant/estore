//import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

packageArchetype.java_application

dockerRepository := Some("enpassant")

packageName in Docker := "backend"

dockerBaseImage := "dockerfile/java"

dockerExposedPorts in Docker := Seq(2551)

//dockerExposedVolumes in Docker := Seq("/opt/docker/logs")

Common.backendSettings("backend")

