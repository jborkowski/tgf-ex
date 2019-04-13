lazy val root = (project in file(".")).
  settings(
    commonSettings,
    consoleSettings,
    compilerOptions,
    typeSystemEnhancements,
    betterMonadicFor,
    dependencies,
    paradise,
    tests
  )

addCommandAlias("fmt", ";scalafmt ;test:scalafmt ;it:scalafmt")

lazy val commonSettings = Seq(
  name := "taglessfinal",
  organization := "com.jobo",
  scalaVersion := "2.12.8"
)

val consoleSettings = Seq(
  initialCommands := s"import com.jobo._",
  scalacOptions in (Compile, console) -= "-Ywarn-unused-import"
)

lazy val compilerOptions =
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-encoding",
    "utf8",
    "-target:jvm-1.8",
    "-feature",
    "-language:_",
    "-Ypartial-unification",
    "-Ywarn-unused-import",
    "-Ywarn-value-discard"
  )

lazy val typeSystemEnhancements =
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9")

lazy val betterMonadicFor =
  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.2.4")

lazy val paradise =
  addCompilerPlugin(("org.scalameta" %% "paradise" % "3.0.0-M11").cross(CrossVersion.full))

def dep(org: String)(version: String)(modules: String*) =
    Seq(modules:_*) map { name =>
      org %% name % version
    }

lazy val dependencies = {
  val akka = dep("com.typesafe.akka")("2.5.22")(
    "akka-actor",
    "akka-stream",
    "akka-testkit"
  )

  val akkaHttp = dep("com.typesafe.akka")("10.1.8")(
    "akka-http",
    "akka-http-spray-json",
    "akka-http-testkit"
  )

  val cats = dep("org.typelevel")("1.6.0")(
    "cats-core"
  )

  val kebs = dep("pl.iterators")("1.6.2")(
    "kebs-slick",
    "kebs-play-json",
    "kebs-tagged-meta"
  )

  val sealedMonad = dep("pl.iterators")("0.0.1-33a3bf8134c261be84bbabae24a744a8629c67c2")(
    "sealed"
  )

  val slick = dep("com.typesafe.slick")("3.3.0")("slick")

  val tmingleiSlick = dep("com.github.tminglei")("0.17.2")(
    "slick-pg",
    "slick-pg_play-json"
  )

  val mixed = Seq(
    "ch.qos.logback" %  "logback-classic" % "1.2.3",
    "com.zaxxer" % "HikariCP" % "3.3.1",
    "org.postgresql" % "postgresql" % "42.2.5",
    "com.beachape" %% "enumeratum-slick" % "1.5.15",
    "com.softwaremill.macwire" %% "macros" % "2.3.1"
  )

  def extraResolvers =
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots"),
      Resolver.bintrayRepo("theiterators", "sealed-monad")
    )

  val deps =
    libraryDependencies ++= Seq(
      akka,
      akkaHttp,
      cats,
      kebs,
      sealedMonad,
      slick,
      tmingleiSlick,
      mixed
    ).flatten

  Seq(deps, extraResolvers)
}

lazy val tests = {
  val dependencies = {
    val scalatest = dep("org.scalatest")("3.0.5")(
       "scalatest"
    )

    val mixed = Seq(
      "org.scalacheck" %% "scalacheck" % "1.14.0"
    )

    libraryDependencies ++= Seq(
      scalatest,
      mixed
    ).flatten.map(_ % "test")
  }

  val frameworks =
    testFrameworks := Seq(TestFrameworks.ScalaTest)

  Seq(dependencies, frameworks)
}
