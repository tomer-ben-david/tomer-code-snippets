import org.apache.spark.{SparkConf, SparkContext}

object SparkExample extends App {
  override def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("parse my book").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val mobyDickText = scala.io.Source.fromURL("http://www.gutenberg.org/files/2701/2701-0.txt").mkString
    val mobyDickLines = mobyDickText.split("\n")
    val mobyDickRDD = sc.parallelize(mobyDickLines)
    val cleanedMobyDick = mobyDickRDD.flatMap(_.split("[^0-9a-zA-Z]")) // split by non alphanumeric chars

    cleanedMobyDick.filter(!_.isEmpty)
      .map(_.toLowerCase())
      .map((_, 1))
      .reduceByKey(_ + _)
      .takeOrdered(10)(Ordering[Int].reverse.on(_._2))
      .foreach(println)

//    cleanedMobyDick.take(10).foreach(println)

  }
}
