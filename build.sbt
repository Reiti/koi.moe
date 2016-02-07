name := "koi.moe"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" % "0.9.3",
  "com.github.finagle" %% "finch-circe" % "0.9.3",
  "com.lihaoyi" %% "scalatags" % "0.5.4",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.4.191"
)