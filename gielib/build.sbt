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
    scalaVersion := "2.11.7",
    scalacOptions := Seq("-optimise", "-Xlint", "-unchecked", "-deprecation", "-encoding", "utf8")
    //libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.4.3"
  ).
  jvmSettings(
    // Add JVM-specific settings here
  ).
  jsSettings(
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"
    // Add JS-specific settings here
    
  )

lazy val gielibJVM = gielib.jvm
lazy val gielibJS = gielib.js
