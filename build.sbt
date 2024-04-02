Global / onChangedBuildSource := ReloadOnSourceChanges

name := "concurrency-in-practice-java"
version := "1.0.0"

scalaVersion := "2.13.12"

javacOptions := Seq("-source", "8", "-target", "8")

// https://docs.scala-lang.org/overviews/compiler-options/
scalacOptions ++= Seq(
  "-feature",      // Emit warning and location for usages of features that should be imported explicitly
  "-explaintypes", // Explain type errors in more detail
  "-deprecation",  // Warn on deprecated API
  "-unchecked",    // Enable warnings where generated code depends on assumptions
  "-language:implicitConversions",
  "-language:existentials",
  "-language:higherKinds",
  "-language:postfixOps",
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-unused",
  "-Ywarn-value-discard",
  "-Ymacro-annotations",
  "-Ywarn-numeric-widen",
  "-Wconf:cat=other-match-analysis:error",
  "-Wunused"
)

lazy val dependencies = List(
  // almost everything you need
  "co.fs2"            %% "fs2-io"                   % "3.9.4",
  // enum-support
  "com.beachape"      %% "enumeratum"               % "1.7.3",
  "com.beachape"      %% "enumeratum-circe"         % "1.7.3",
  "com.beachape"      %% "enumeratum-doobie"        % "1.7.3",
  "com.beachape"      %% "enumeratum-cats"          % "1.7.3",
  "com.beachape"      %% "enumeratum-scalacheck"    % "1.7.3",
  // test environment
  "org.scalatest"     %% "scalatest"                % "3.2.17",
  "org.scalacheck"    %% "scalacheck"               % "1.17.0",
  "org.scalatestplus" %% "scalacheck-1-17"          % "3.2.17.0",
  "org.mockito"       %% "mockito-scala-scalatest"  % "1.17.30",
  "com.lihaoyi"       %% "pprint"                   % "0.8.1",
  // concurrency (java)
  "org.openjdk.jmh"    % "jmh-core"                 % "1.37",
  "org.openjdk.jmh"    % "jmh-generator-annprocess" % "1.37",
  "edu.umd.cs.mtc"     % "multithreadedtc"          % "1.01",
  "junit"              % "junit"                    % "4.13.2"
)

libraryDependencies ++= Seq(
  /** some useful plugin things */
  compilerPlugin("org.typelevel"  %% "kind-projector"  % "0.13.2" cross CrossVersion.full), // https://github.com/typelevel/kind-projector
  compilerPlugin("org.augustjune" %% "context-applied" % "0.1.4")                           // https://github.com/augustjune/context-applied
) ++ dependencies

//enablePlugins(JmhPlugin)
