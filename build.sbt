//import AssemblyKeys._
import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

//assemblySettings

//enablePlugins(DockerPlugin)

//packageArchetype.java_application

maintainer in Docker := "Enpassant <fkalman@index.hu>"

//mappings in Docker := mappings.value

version in Docker := version.value

defaultLinuxInstallLocation in Docker := "/opt/docker"

lazy val frontend = (project in file("frontend"))
  .enablePlugins(PlayScala)
  .dependsOn(api).aggregate(api)

lazy val backend = (project in file("backend"))
  .settings(assemblySettings: _*)
  .dependsOn(api).aggregate(api)

lazy val api = (project in file("api"))

//
// Scala Compiler Options
// If this project is only a subproject, add these to a common project setting.
//
scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.7",
  "-encoding", "UTF-8",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code"
)

