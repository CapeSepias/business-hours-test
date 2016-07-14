import sbt._

object Dependencies {

  object Versions {
    val slf4j = "1.7.10"
    val junit = "4.11"
    val specs2 = "2.4.2"
  }

  def asCompile (dep: ModuleID): Seq[ModuleID] = Seq(dep) map (_ % "compile")
  def asCompile (deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "compile")
  def asProvided (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def asTest (dep: ModuleID): Seq[ModuleID] = Seq(dep) map (_ % "test")
  def asTest (deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "test")
  def asRuntime (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def asContainer (dep: ModuleID): Seq[ModuleID] = Seq(dep) map (_ % "container")
  def asContainer (deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "container")


  val slf4jApi = "org.slf4j" % "slf4j-api" % Versions.slf4j
  val casbah = "org.mongodb" %% "casbah" % "2.8.2"
  val casbahGridFs = "org.mongodb" %% "casbah-gridfs" % "2.8.2"
  val sprayJson = "io.spray" %% "spray-json" % "1.3.1"
  val slf4s = "org.slf4s" %% "slf4s-api" % "1.7.10"
  val slf4jSimple = "org.slf4j" % "slf4j-simple" % Versions.slf4j
  val commonsIo = "commons-io" % "commons-io" % "1.3.2"
  val commonsCodec = "commons-codec" % "commons-codec" % "1.7"
  val commonsLang = "org.apache.commons" % "commons-lang3" % "3.1"
  val nscalaTime = "com.github.nscala-time" %% "nscala-time" % "1.0.0"
  val jetm = "fm.void.jetm" % "jetm" % "1.2.3"

  val junit = "junit" % "junit" % Versions.junit
  val specs2 = Seq(
    "org.specs2" %% "specs2" % Versions.specs2,
     // add junit for XML result generation
    junit)

}
