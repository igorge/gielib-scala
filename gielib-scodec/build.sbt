name := "gielib-scodec"

lazy val root = project.in(file(".")).
  aggregate(gielibJS, gielibJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val gielib = crossProject.in(file(".")).
  settings(
    organization := "gie",
    name := "gielib-scodec",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.11.8",
    scalacOptions := Seq("-optimise", "-Xlint", "-unchecked", "-deprecation", "-encoding", "utf8"),
    libraryDependencies += "org.scodec" %%% "scodec-core" % "1.8.1"
  ).
  jvmSettings(
    // Add JVM-specific settings here
  ).
  jsSettings(
    // Add JS-specific settings here
    
  )

lazy val gielibJVM = gielib.jvm
lazy val gielibJS = gielib.js
