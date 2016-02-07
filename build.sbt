name := "koi.moe"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" % "0.9.3",
  "com.github.finagle" %% "finch-circe" % "0.9.3"
)