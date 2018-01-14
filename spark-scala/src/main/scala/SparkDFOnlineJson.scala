import org.apache.spark.sql.Row

object SparkDFOnlineJson extends App {

  override def main(args: Array[String]): Unit = {

    val jsonString = scala.io.Source.fromURL("https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=ETH&limit=30&aggregate=1&e=CCCAGG").mkString

    val spark = org.apache.spark.sql.SparkSession.builder().appName("someapp").master("local[*]").getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._
    val df = spark.read.json(Seq(jsonString).toDS())

    df.show()

    df.take(10).foreach(println)
    df.printSchema()

    df.select($"Data.close".as("close_price")).show(2) // <-- HERE reading Data.close from the json!

    val jsonExplodedDF = df.select($"Aggregated", $"ConversionType", explode($"Data").as("prices")) // <-- HERE reading Data.close from the json!
    jsonExplodedDF.printSchema()
    jsonExplodedDF.select($"Aggregated", $"ConversionType", $"prices".getItem("close")).show(10) // Then getItem instead of explode to objects!! because we have hierarchy.

  }
}

//[false,[,invert],WrappedArray([26.43,33.57,26.02,32.47,1513036800,75912.7,2252319.3], [23.41,27.59,22.83,26.43,1513123200,68652.13,1714092.19], [23.91,25.06,21.87,23.39,1513209600,62691.53,1452942.54], [25.87,29.03,23.88,23.91,1513296000,50825.4,1342967.63], [28.11,28.62,24.53,25.87,1513382400,38155.01,1013078.48], [26.72,28.11,25.93,28.11,1513468800,36242.76,979762.25], [24.08,26.86,23.29,26.72,1513555200,46712.69,1186390.62], [21.63,24.41,21.29,24.08,1513641600,65125.17,1449434.45], [20.67,22.29,20.42,21.63,1513728000,64539.45,1372742.27], [19.79,20.94,19.4,20.67,1513814400,61802.62,1244602.57], [20.93,21.98,19.47,19.79,1513900800,80230.91,1656134.49], [20.78,20.97,20.42,20.93,1513987200,42893.35,887428.82], [20.53,20.97,20.36,20.77,1514073600,41294.18,855012.67], [19.18,20.53,18.67,20.53,1514160000,48165.25,929653.57], [20.91,21.55,18.75,19.18,1514246400,46999.33,956924.92], [20.88,21.57,20.45,20.91,1514332800,36759.37,769083.49], [20.04,20.95,19.7,20.88,1514419200,40883.16,828193.82], [19.58,20.25,19.32,20.04,1514505600,43487.34,857520.42], [18.14,19.77,18.09,19.58,1514592000,66161.84,1246949.13], [18.68,19.07,18.05,18.14,1514678400,48718.02,902419.05], [17.76,18.7,17.54,18.67,1514764800,50703.72,910875.63], [17.16,18.94,15.25,17.76,1514851200,96092.61,1574640.02], [16.01,17.68,15.62,17.16,1514937600,75289.68,1266911.61], [16.06,16.59,14.43,16.03,1515024000,80755.25,1258516.2], [17.59,18.29,14.54,16.07,1515110400,104693.19,1682729.53], [17.03,17.91,16.25,17.59,1515196800,58014.94,975679.49], [14.49,17.06,14.47,17.03,1515283200,64620.79,994739.35], [13.2,14.5,12.73,14.49,1515369600,102880.99,1380565.72], [11.18,13.21,10.93,13.2,1515456000,95751.66,1168583.78], [11.95,12.06,10.16,11.18,1515542400,143351.13,1546032.52], [11.57,11.96,10.93,11.95,1515628800,64956.27,730014.1]),true,Success,1513036800,1515628800,100]
//root
//|-- Aggregated: boolean (nullable = true)
//|-- ConversionType: struct (nullable = true)
//|    |-- conversionSymbol: string (nullable = true)
//|    |-- type: string (nullable = true)
//|-- Data: array (nullable = true)
//|    |-- element: struct (containsNull = true)
//|    |    |-- close: double (nullable = true)
//|    |    |-- high: double (nullable = true)
//|    |    |-- low: double (nullable = true)
//|    |    |-- open: double (nullable = true)
//|    |    |-- time: long (nullable = true)
//|    |    |-- volumefrom: double (nullable = true)
//|    |    |-- volumeto: double (nullable = true)
//|-- FirstValueInArray: boolean (nullable = true)
//|-- Response: string (nullable = true)
//|-- TimeFrom: long (nullable = true)
//|-- TimeTo: long (nullable = true)
//|-- Type: long (nullable = true)