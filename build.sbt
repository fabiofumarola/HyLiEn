lazy val commons = Seq(
  organization := "it.datatoknowledge",
  name := "HyLiEn",
  version := "1.0.0",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-target:jvm-1.8", "-feature"),
  resolvers ++= Seq(
    "spray repo" at "http://repo.spray.io",
    Resolver.sonatypeRepo("public"),
    Resolver.typesafeRepo("releases")
  )
)

lazy val root = (project in file("."))
 .settings(commons: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.machinepublishers" % "jbrowserdriver" % "0.14.2",
      "org.jsoup" % "jsoup" % "1.9.2",
      "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.4.0",
      "ch.qos.logback" % "logback-classic" % "1.1.7",
      "com.rockymadden.stringmetric" % "stringmetric-core_2.11" % "0.27.4"
    )
  )
