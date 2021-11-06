name := "AwsLambdaUsinggRPC"

version := "0.1"

scalaVersion := "2.13.6"

val logbackVersion = "1.3.0-alpha10"
val sfl4sVersion = "2.0.0-alpha5"
val typesafeConfigVersion = "1.4.1"
val apacheCommonIOVersion = "2.11.0"
val scalacticVersion = "3.2.9"
val scalapbVersion = "0.11.4"
val generexVersion = "1.0.2"
val awsVersion = "1.12.89"
val grpcVersion = "1.41.0"
val AkkaVersion = "2.6.14"

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  "com.amazonaws" % "aws-lambda-java-core" % "1.0.0",
  "com.amazonaws" % "aws-java-sdk-lambda" % awsVersion,
  "com.amazonaws" % "aws-java-sdk" % awsVersion,
  "com.amazonaws" % "aws-java-sdk-s3" % awsVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-awslambda" % "3.0.3",
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  "org.json" % "json" % "20090211",
  "junit" % "junit" % "4.13.2",
  "javax.xml.bind" % "jaxb-api" % "2.3.0",
  "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % "test",
  "ch.qos.logback" % "logback-core" % logbackVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.slf4j" % "slf4j-api" % sfl4sVersion,
  "com.typesafe" % "config" % typesafeConfigVersion,
  "io.spray" %% "spray-json" % "1.3.5",
  "commons-io" % "commons-io" % apacheCommonIOVersion,
  "org.scalactic" %% "scalactic" % scalacticVersion,
  "org.scalatest" %% "scalatest" % scalacticVersion % Test,
  "org.scalatest" %% "scalatest-featurespec" % scalacticVersion % Test,
  "com.typesafe" % "config" % typesafeConfigVersion,
  "com.github.mifmif" % "generex" % generexVersion
)
