import AssemblyKeys._
import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

assemblySettings

//enablePlugins(DockerPlugin)

packageArchetype.java_application

maintainer in Docker := "Enpassant <fkalman@index.hu>"

//mappings in Docker := mappings.value

packageName in Docker := "backend"

dockerRepository := Some("enpassant")

version in Docker := version.value

dockerBaseImage := "dockerfile/java"

dockerExposedPorts in Docker := Seq(2551)

//dockerExposedVolumes in Docker := Seq("/opt/docker/logs")

//dockerEntrypoint in Docker := "java -jar /opt/docker/backend.jar"

defaultLinuxInstallLocation in Docker := "/opt/docker"

Common.backendSettings("backend")

