name := "ergle"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache,
    "org.springframework" % "spring-context" % "3.2.2.RELEASE",
    "javax.inject" % "javax.inject" % "1",
    "org.mockito" % "mockito-core" % "1.9.5"
)

play.Project.playScalaSettings

packageArchetype.java_application
