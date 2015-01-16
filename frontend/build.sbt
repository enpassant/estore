import NativePackagerKeys._

packageArchetype.java_application

dockerExposedPorts in Docker := Seq(9000)

dockerRepository := Some("enpassant")

packageName in Docker := "frontend"

dockerBaseImage := "dockerfile/java"

Common.frontendSettings

