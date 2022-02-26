ThisBuild / scalaVersion := "2.13.8"
ThisBuild / fork := true

val grpc = Seq(
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
)

val cassandra = Seq(
  "com.datastax.oss" % "java-driver-core" % "4.13.0",
  "com.datastax.oss" % "java-driver-query-builder" % "4.13.0"
)

val other = Seq(
  "com.typesafe" % "config" % "1.4.1",
  "com.google.inject" % "guice" % "5.0.1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3",
  "ch.qos.logback" % "logback-classic" % "1.2.10",
  "org.scalatest" %% "scalatest" % "3.2.10" % Test
)

lazy val service = project.settings(
  libraryDependencies ++= grpc ++ cassandra ++ other,
  Compile / PB.targets := Seq(
    scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
  )
)
