import org.apache.spark.{SparkConf, SparkContext}
import org.specs2.mutable.Specification

import scala.util.Random

class SparkTestExample extends Specification {
  def sc = SparkContext.getOrCreate(new SparkConf().setAppName("parse my book").setMaster("local[*]"))

  "this is my specification" should {
    "have one example" in {
      val rdd = sc.makeRDD(List(1,2))
      val filteredRDD = rdd.filter(_ != 1)
      filteredRDD.collect().length must_== 1
    }
  }

  def buildTestSparkConf(): SparkConf = {
    new SparkConf().setAppName("test-app")
        .setMaster("local[*]")
      .set("spark.driver.allowMultipleContexts", "true")
      .set("spark.app.id", s"${System.currentTimeMillis()}-${Random.nextInt(100)}")
  }
}
