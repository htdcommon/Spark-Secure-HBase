name := "Spark-Secure-HBase"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.0.0" % "provided",
  "com.databricks" %% "spark-avro" % "3.1.0",
  "org.apache.spark" %% "spark-sql" % "2.0.0" % "provided",
  "org.apache.hbase" % "hbase-client" % "2.0.5",
  "org.apache.hbase" % "hbase-common" % "2.0.5",
  "org.apache.hbase" % "hbase-mapreduce" % "2.0.5"
)