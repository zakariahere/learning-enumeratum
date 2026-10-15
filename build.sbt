ThisBuild / scalaVersion := "2.13.18"

lazy val root = (project in file("."))
  .settings(
    name := "enumeratum-playground",
    scalacOptions ++= Seq("-deprecation", "-feature", "-Xlint"),
    libraryDependencies ++= Seq(
      "com.beachape" %% "enumeratum" % "1.9.0"
    )
  )
