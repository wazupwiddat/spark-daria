import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

SbtScalariform.scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(SpacesAroundMultiImports, false)

resolvers += "jitpack" at "https://jitpack.io"

name := "spark-daria"

version := "0.19.0"

scalaVersion := "2.11.12"
val sparkVersion = "2.3.0"
val sparkFastTestsVersion = s"v${sparkVersion}_0.8.0"

libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
libraryDependencies += "org.apache.commons" % "commons-text" % "1.1" % "provided"

libraryDependencies += "com.github.mrpowers" % "spark-fast-tests" % sparkFastTestsVersion % "test"
libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.3" % "test"
testFrameworks += new TestFramework("utest.runner.Framework")

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "_" + sv.binary + "-" + sparkVersion + "_" + module.revision + "." + artifact.extension
}

// All Spark Packages need a license
licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

credentials += Credentials(Path.userHome / ".ivy2" / ".sbtcredentials")

fork in Test := true

javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:+CMSClassUnloadingEnabled","-Duser.timezone=GMT")

/* Only invoked when you do `doc` in SBT */
scalacOptions in (Compile, doc) += "-groups"
