// dependency graph visualisation
addDependencyTreePlugin
// code formatting
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.2")
// bulid server protocol
addSbtPlugin("ch.epfl.scala" % "sbt-bloop"    % "1.5.13")
// JMH integration: https://github.com/sbt/sbt-jmh
//addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.4.3")