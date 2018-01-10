import org.apache.spark.{SparkConf, SparkContext}

object SparkDF extends App {

  override def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("parse my book").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile("file:///Users/tomer.bendavid/dev/spark-data/donation") // https://bit.ly/1Aoywaq extract and leave only block_...csv
    val rddWithoutHeader = rdd.filter(!_.contains("id_d1"))
    println(rddWithoutHeader.take(10).foreach(println))

    val spark = org.apache.spark.sql.SparkSession.builder().appName("someapp").getOrCreate()

    val df = spark.read
        .option("header", "true") // treat first line as header.
        .option("nullValue", "?") // treat ? as nulls.
        .option("inferSchema", "true")
        .csv("file:///Users/tomer.bendavid/dev/spark-data/donation")

    df.show()
    df.printSchema()

  }
}
