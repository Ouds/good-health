package word

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

/**
 * @author ZZY
 * 统计字符出现个数
 *
 */
object WordCount4s {

  def main(args: Array[String]) {
    val ioArgs = Array("input", "spark_wc")

    val conf = new SparkConf()
      .setMaster("spark://172.25.5.2:7077")
      //.setMaster("yarn")
      .setAppName("wc4scala@spark")
    val sc = new SparkContext(conf)

    // SparkContext 是把代码提交到集群或者本地的通道，我们编写Spark代码，无论是要本地运行还是集群运行都必须有SparkContext的实例
    val line = sc.textFile(ioArgs(0))

    // 把读取的内容保存给line变量，其实line是一个MappedRDD，Spark的所有操作都是基于RDD的
    val result = line.flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)

    result.collect.foreach(println)
    result.saveAsTextFile(ioArgs(1))

    sc.stop
  }

}
