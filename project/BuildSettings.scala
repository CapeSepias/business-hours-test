import sbt._
import sbtrelease.ReleasePlugin._

object BuildSettings {
  import Keys._


  lazy val basicSettings = Seq(
    // version is set via sbt-release, see versions.sbt
    organization          := "com.cupenya",
    scalaVersion          := "2.11.7",
    credentials           += Credentials(Path.userHome / ".sbt" / ".credentials"),
    javaOptions           += "-XX:+HeapDumpOnOutOfMemoryError",
    javaOptions           += "-Xmx1G",
    scalacOptions         := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.7",
      "-Xlog-reflective-calls"
    ),
    // for WAR packing
    exportJars           := true,

    // generate junit report
    testOptions in Test += Tests.Argument(TestFrameworks.Specs2, "console", "junitxml")
  )


  lazy val crossCompileSettings = Seq(
    crossScalaVersions := Seq("2.10.6", "2.11.7")
  )
}
