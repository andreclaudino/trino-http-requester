ThisBuild / scalaVersion := "2.13.8"

val projectName = "trino-plugins"
val trinoVersion = "395"
ThisBuild / version := trinoVersion

lazy val root = (project in file("."))
  .settings(
    name := "trino-http-requester",
    idePackagePrefix := Some("com.d2x.trinoplugins")
  )


libraryDependencies ++= Seq(
  "io.trino" % "trino-spi" % trinoVersion % "provided",
  "io.trino" % "trino-plugin-toolkit" % trinoVersion,
  "com.google.guava" % "guava" % "31.1-jre",
  "org.scalaj" %% "scalaj-http" % "2.4.2",
)

assembly / assemblyJarName := "http-requester.jar"
assembly / assemblyOutputPath := file(s"target/http-requester/${(assembly/assemblyJarName).value}")


assembly / assemblyMergeStrategy := {
  case PathList("io", "trino", "spi", "license", "LicenseManager.class") => MergeStrategy.discard
  case PathList("META-INF", "services", "io.trino.spi.Plugin") => MergeStrategy.first
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}