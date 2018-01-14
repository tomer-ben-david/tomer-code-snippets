import org.apache.spark.{SparkConf, SparkContext}

object SparkDFAndSQL extends App {

  override def main(args: Array[String]): Unit = {
//Notebook:2D3P7TM8K,8
    //http://localhost:8080/#/notebook/2D3P7TM8K    // Operations on columns\

//Paragraph:20180112-201223_1357158255,5
    val conf = new SparkConf().setAppName("parse my book").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile("file:///Users/tomer.bendavid/dev/spark-data/donation") // https://bit.ly/1Aoywaq extract and leave only block_...csv
    val rddWithoutHeader = rdd.filter(!_.contains("id_d1"))
    println(rddWithoutHeader.take(10).foreach(println))

    val spark = org.apache.spark.sql.SparkSession.builder().appName("someapp").getOrCreate()

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
