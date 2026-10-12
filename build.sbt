name := "scala-lab"
version := "1.0"
scalaVersion := "2.13.12"
libraryDependencies ++= Seq(
  "org.scalanlp" %% "breeze" % "2.1.0",
  "org.scalanlp" %% "breeze-viz" % "2.1.0",
  "org.apache.commons" % "commons-math3" % "3.6.1"
)
fork := true
