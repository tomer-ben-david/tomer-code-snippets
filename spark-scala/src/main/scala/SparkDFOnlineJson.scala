

object SparkDFOnlineJson extends App {

  override def main(args: Array[String]): Unit = {

    val jsonString = scala.io.Source.fromURL("https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=ETH&limit=30&aggregate=1&e=CCCAGG").mkString

    val spark = org.apache.spark.sql.SparkSession.builder().appName("someapp").master("local[*]").getOrCreate()

    import spark.implicits._
    val df = spark.read.json(Seq(jsonString).toDS())

    df.show()

    df.take(10).foreach(println)
    df.printSchema()

  }
}
