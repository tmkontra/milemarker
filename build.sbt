ThisBuild / scalaVersion     := "3.3.3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.github.tmkontra"
ThisBuild / organizationName := "tmkontra"

lazy val root = (project in file("."))
  .settings(
    name := "milemarker",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.1",
      "dev.zio" %% "zio-test" % "2.1.1" % Test,
      "org.springframework.boot" % "spring-boot-starter-data-jpa" % "3.3.3",
      "org.springframework.data" % "spring-data-jdbc" % "3.3.3",
      "org.postgresql" % "postgresql" % "42.7.2",
      "dev.zio" %% "zio-http" % "3.0.0-RC9"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
