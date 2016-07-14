import pl.project13.scala.sbt.JmhPlugin
import sbt.Keys._
import sbt._

object Build extends Build {
  import BuildSettings._
  import Dependencies._

  lazy val root = Project(id = "business-hours-test",
                          base = file("."))
    .settings(basicSettings: _*)
    .settings(crossCompileSettings: _*)
    .aggregate(businessHours, businessHoursBenchmark)


  lazy val businessHours = Project(id = "business-hours",
                          base = file("business-hours"))
    .settings(basicSettings: _*)
    .settings(crossCompileSettings: _*)
    .settings(libraryDependencies ++=
      asCompile(sprayJson) ++
      asCompile(nscalaTime) ++
      asTest(specs2)
    )

  lazy val businessHoursBenchmark = Project(id = "business-hours-benchmark",
                                          base = file("business-hours-benchmark"))
    .dependsOn(businessHours)
    .settings(basicSettings: _*)
    .settings(crossCompileSettings: _*)
    .enablePlugins(JmhPlugin)
}
