package com.sensetime


import java.security.PrivilegedExceptionAction

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Get}
import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.hbase.util.Bytes;

object HBaseTest {
  def main(args: Array[String]): Unit = {
    System.setProperty("java.security.krb5.conf", "/etc/krb5.conf")

    val conf = new SparkConf().setAppName("SparkSecureHBase")
    val sc = new SparkContext(conf)

    val hbaseconf = HBaseConfiguration.create;
    hbaseconf.set("hbase.zookeeper.quorum", "node-01");
    hbaseconf.set("hbase.zookeeper.property.clientPort", "2181");
    hbaseconf.set("hadoop.security.authentication", "Kerberos");

    UserGroupInformation.setConfiguration(hbaseconf);

    val ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI("hatieda@HADOOP-VIDEO.DATA.SENSETIME.COM", "/home/hatieda/hatieda.hadoop-video.keytab");
    UserGroupInformation.setLoginUser(ugi)
    ugi.doAs(new PrivilegedExceptionAction[Void]() {

      override def run: Void = {
        val connection = ConnectionFactory.createConnection(hbaseconf)
        println(connection)

        val table = connection.getTable(TableName.valueOf("member"))
        val get = new Get(Bytes.toBytes("1000"))
        val result = table.get(get)

        println(result)

        null
      }
    })

    sc.stop()
  }

}
