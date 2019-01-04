package bd

import org.apache.spark.sql.{ SparkSession, DataFrame }

import com.mongodb.spark._
import org.bson.Document

import com.mongodb.spark.MongoSpark

object MongoTest {

  def main(args: Array[String]) {
    val ss = SparkSession.builder()
      .master("spark://隐藏")
      .appName("测试MongoDB读写")
      .config("spark.mongodb.input.uri", "mongodb://隐藏/bigdata.jobs")
      .config("spark.mongodb.output.uri", "mongodb://隐藏:27017/bigdata.jobs_test")
      .config("spark.executor.memory", "4g")
      .getOrCreate()

    val documents = ss.sparkContext.parallelize((1 to 10).map(i => Document.parse(s"{test: $i}")))
    MongoSpark.save(documents)

  }

}



