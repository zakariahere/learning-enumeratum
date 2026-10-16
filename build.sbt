ThisBuild / scalaVersion := "2.13.18"

lazy val root = (project in file("."))
  .settings(
    name := "enumeratum-playground",
    scalacOptions ++= Seq("-deprecation", "-feature", "-Xlint"),
    libraryDependencies ++= Seq(
      "com.beachape" %% "enumeratum"       % "1.9.0",
      "com.beachape" %% "enumeratum-circe" % "1.9.0",
      "io.circe"     %% "circe-generic"    % "0.14.10",
      "io.circe"     %% "circe-parser"     % "0.14.10"
    )
  )
