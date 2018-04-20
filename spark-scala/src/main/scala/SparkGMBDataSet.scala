import org.apache.spark.{SparkConf, SparkContext}

object SparkGMBDataSet extends App {

  override def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("parse my book").setMaster("local[*]")
    val sc = new SparkContext(conf)


//Notebook:2D6MA58FK,2
//http://localhost:8080/#/notebook/2D6MA58FK

    val spark = org.apache.spark.sql.SparkSession.builder().appName("someapp").getOrCreate()
//Paragraph:20180127-180413_389214480,1
    val gmbText = spark.read.text("file:///Users/tomer.bendavid/Downloads/gmb-2.2.0/data/p00/d0018/*.tags")
    gmbText.foreach(line => println(line.toString()))


    val rdd = sc.textFile("file:///Users/tomer.bendavid/dev/spark-data/donation") // https://bit.ly/1Aoywaq extract and leave only block_...csv
    val rddWithoutHeader = rdd.filter(!_.contains("id_d1"))
    println(rddWithoutHeader.take(10).foreach(println))


    val parsed = spark.read
        .option("header", "true") // treat first line as header.
        .option("nullValue", "?") // treat ? as nulls.
        .option("inferSchema", "true")
        .csv("file:///Users/tomer.bendavid/dev/spark-data/donation")

    parsed.show()
    parsed.printSchema()

    parsed.describe().show() // like summary in R.

    val matches = parsed.where("is_match = true")
    val matchSummary = matches.describe()

    import spark.implicits._
    parsed.groupBy("is_match").count().orderBy($"count".desc).show() // count column is_match true

    import org.apache.spark.sql.functions._
    parsed.agg(avg($"cmp_sex")).show() // aggregation of column, average.

    parsed.createTempView("linkage") // Tables and spark sql
    spark.sql("SELECT is_match FROM linkage").show()

  }
}
