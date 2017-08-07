name := """ngs2-user-router"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "org.mindrot" % "jbcrypt" % "0.4",
  javaWs,
  "org.apache.commons" % "commons-csv" % "1.4"
)
libraryDependencies += evolutions

playEbeanModels in Compile := Seq("models.*")
playEbeanDebugLevel := 4