package site

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object ValidFavorites4s {

  def main(args: Array[String]) {
    if (args.length < 1) {
      System.err.println("Usage: <file>")
      System.exit(1)
    }

    val conf = new SparkConf()
    val sc = new SparkContext(conf)
    val line = sc.textFile(args(0))

    line.map(_.replace(" ", "")).map(s => {
      var split = s.split("|")
      var ip = split(0);
      var num = Integer.parseInt(split(2));
      (ip, num)
    }).reduceByKey((a: Int, b: Int) => {
      if (a > b)
        a
      else
        b
    }).collect().foreach(println)

    sc.stop()
  }
}

