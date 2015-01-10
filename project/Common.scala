import sbt._
import Keys._
import play.PlayImport._
import com.typesafe.sbt.web.SbtWeb.autoImport.{Assets, pipelineStages}
import com.typesafe.sbt.less.Import.LessKeys
import com.typesafe.sbt.rjs.Import.{rjs, RjsKeys}
import com.typesafe.sbt.digest.Import.digest
import com.typesafe.sbt.gzip.Import.gzip

object Common {
  def appName = "estore"

  val buildVersion = "1.0-SNAPSHOT"

  // Common settings for every project
  def settings (theName: String) = Seq(
    resolvers += Resolver.sonatypeRepo("snapshots"),
    name := theName,
    organization := "com.myweb",
    version := buildVersion,
    //              scalaVersion := "2.11.1",
    shellPrompt  := ShellPrompt.buildShellPrompt,
    scalaVersion := "2.11.1",
    doc in Compile <<= target.map(_ / "none"),
    scalacOptions ++= Seq("-feature", "-deprecation", "-unchecked", "-language:reflectiveCalls")
  )

  // Settings for the app, i.e. the root project
  val frontendSettings = settings(appName) ++: Seq(
    libraryDependencies ++= (Dependencies.frontend  ++ Seq(filters, cache)),
    includeFilter in (Assets, LessKeys.less) := "*.less",
    excludeFilter in (Assets, LessKeys.less) := "_*.less",
    pipelineStages := Seq(rjs, digest, gzip),
    RjsKeys.mainModule := s"main",
    RjsKeys.mainConfig := s"main"
//    RjsKeys.mainModule := s"main-$appName",
//    RjsKeys.mainConfig := s"build-$appName"
//    RjsKeys.paths += ("jsRoutes" -> ("/jsroutes" -> "empty:"))
  )

  // Settings for every module, i.e. for every subproject
  def backendSettings (module: String) = settings(module) ++: Seq(
    javaOptions in Test += s"-Dconfig.resource=test.conf",
    libraryDependencies ++= Dependencies.backend,
    javaOptions in run ++= Seq(
        "-Djava.library.path=./sigar",
        "-Xms128m", "-Xmx1024m"),
    // this enables custom javaOptions
    fork in run := true
  )

  // Shell prompt which show the current project,
  // git branch and build version
  object ShellPrompt {
    object devnull extends ProcessLogger {
      def info (s: => String) {}
      def error (s: => String) { }
      def buffer[T] (f: => T): T = f
    }
    def currBranch = (("git branch" #| Process(Seq("sed", "-e", "/^\\s/d", "-e", "s/^\\*\\s//"))
      lines_! devnull headOption) getOrElse "-")

    val buildShellPrompt = {
      (state: State) => {
        val currProject = Project.extract (state).getOpt(sbt.Keys.name) getOrElse {
          Project.extract(state).currentProject.id
        }
        scala.Console.CYAN + "[%s:%s:%s] ".format (
          currProject, currBranch, Common.buildVersion
        ) + scala.Console.RESET
      }
    }
  }
}
