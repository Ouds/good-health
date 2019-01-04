package database

import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.sql.{ SQLContext, Row }
import org.apache.spark.sql.types.{ StringType, IntegerType, StructField, StructType }

object ReadMysql {
  def main(args: Array[String]) {
    val conf = new SparkConf()
              .setMaster("spark://192.168.252.137:7077")
              .setAppName("ReadMysql")
    val sc = new SparkContext(conf)
    val sqlc = new SQLContext(sc)

    /**
     * 定义mysql的信息
     * ?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
     */
    val url = "jdbc:mysql://192.168.49.45:3306/xinong"
    val props = new java.util.Properties
    props.setProperty("user", "root")
    props.setProperty("password", "mysql")

    /**
     * 指定读取条件
     * spark_test为表名，注意用相对路径
     * Array("id='6'") 是where过滤条件，返回类型为Array
     */
    val table = "spark_test"
    
    val tableAllDF = sqlc.read.jdbc(url, table, props)
    tableAllDF.show()
    
    val tableFilterDF = sqlc.read.jdbc(url, table, Array("id='6'"), props)
    tableFilterDF.show()
    
    sc.stop()
  }
}

object WriteMysql {
  def main(args: Array[String]) {
    val conf = new SparkConf()
              .setMaster("spark://192.168.252.137:7077")
              .setAppName("WriteMysql")
    val sc = new SparkContext(conf)
    val sqlc = new SQLContext(sc)

    /**
     * 定义mysql的信息
     * ?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
     */
   val url = "jdbc:mysql://192.168.49.45:3306/xinong"
    val props = new java.util.Properties
    props.setProperty("user", "root")
    props.setProperty("password", "mysql")
    
    /** 
     *  通过并行化创建RDD
     */
    val valueRDD = sc.parallelize(Array("23 德阳 8", "24 泸州 13", "25 上海 14")).map(_.split(" "))
                   
    // 通过StructType直接指定每个字段的schema
    val schema = StructType(
      List(
        StructField("id", IntegerType, true),
        StructField("value_test", StringType, true),
        StructField("pid", IntegerType, true)
      )
    )
    
    // 将RDD映射到rowRDD
    val rowRDD = valueRDD.map(p => Row(p(0).toInt, p(1).trim, p(2).toInt))
    // 将schema信息应用到rowRDD上
    val valueDataFrame = sqlc.createDataFrame(rowRDD, schema)
    
    //将数据追加到数据库
    val table = "spark_test"
    valueDataFrame.write.mode("append").jdbc(url, table, props)
    
    sc.stop()
  }
}


