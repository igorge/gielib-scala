name := "gielib"

lazy val root = project.in(file(".")).
  aggregate(gielibJS, gielibJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val gielib = crossProject.in(file(".")).
  settings(
    organization := "gie",
    name := "gielib",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.11.8",
    scalacOptions := Seq("-optimise", "-Xlint", "-unchecked", "-deprecation", "-encoding", "utf8"),
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.1" % "test",
    libraryDependencies += "org.scalaz" %%% "scalaz-core" % "7.2.2",
    testFrameworks += new TestFramework("utest.runner.Framework")
  ).
  jvmSettings(
    // Add JVM-specific settings here
  ).
  jsSettings(
    jsDependencies += RuntimeDOM,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"
    // Add JS-specific settings here
    
  )

lazy val gielibJVM = gielib.jvm
lazy val gielibJS = gielib.js
