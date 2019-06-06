/* Simple app to inspect SFPD data */
/* The following import statement is importing SparkSession*/

package maprpoc

import java.sql.DriverManager

import com.mapr.db.spark.api.java.MapRDBJavaSparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SQLContext
import com.mapr.db.MapRDB
import com.mapr.db.Table
//import org.ojai.Document
//import org.ojai.DocumentStream
//import org.ojai.exceptions
//import org.ojai._
//import org.ojai.store.DocumentMutation
//import org.ojai.store.QueryCondition


object POCApp {
  def main(args: Array[String]) {

    val spark = SparkSession.builder.master("local").appName("POCApp").getOrCreate()


    val sfpdFile = "/user/user01/Data/sfpd.csv"
    val pocFile = PocConstants.INPUT_FILE_DIR+"/tripdata.csv"
    System.out.println("pocFile==>"+pocFile.toString)


    //Trip data data column names
    case class TripDataSchema(
    VendorID:String,
    tpep_pickup_datetime:String,
    tpep_dropoff_datetime:String,
    passenger_count:Integer,
    trip_distance:Double,
    RatecodeID:Integer,
    store_and_fwd_flag:String,val sfpdDS = spark.read.format("csv").option("IncidentsSchema", true)
      .load("/user/user01/Data/sfpd.csv")
      .toDF("incidentnum", "category", "description", "dayofweek", "date", "time", "pddistrict", "resolution", "address", "X", "Y", "pdid")
      .cache
    PULocationID:String,
    DOLocationID:String,
    payment_type:String,
    fare_amount:Double,
    extra:String,
    mta_tax:Double,
    tip_amount:Double,
    tolls_amount:Double,
    improvement_surcharge:Double,
    total_amount:Double)

    //Build Schema for Spark record storage
    case class IncidentsSchema(incidentnum:String, category:String,
                               description:String, dayofweek:String, date:String,
                               time:String, pddistrict:String, resolution:String,
                               address:String, x:Double, y:Double, pdid:String)

    val pocDS = spark.read.format("csv").option("TripDataSchema", true)
//      .load(pocFile)
      .load("/user/user01/Data/tripdata.csv")
      .toDF("VendorID",
        "tpep_pickup_datetime",
        "tpep_dropoff_datetime",
        "passenger_count",
        "trip_distance",
        "RatecodeID",
        "store_and_fwd_flag",
        "PULocationID",
        "DOLocationID",
        "payment_type",
        "fare_amount",
        "extra",
        "mta_tax",
        "tip_amount",
        "tolls_amount",
        "improvement_surcharge",
        "total_amount")
      .cache


//    val sfpdDS = spark.read.format("csv").option("IncidentsSchema", true)
//      .load("/user/user01/Data/sfpd.csv")
//      .toDF("incidentnum", "category", "description", "dayofweek", "date", "time", "pddistrict", "resolution", "address", "X", "Y", "pdid")
//      .cache


//    // TO DO: Calculate total number of incidents
//    val sfpdCount = sfpdDS.count()
//
//    // TO DO: Select distinct Categories of incidents
//    val sfpdCategory = sfpdDS.select("Category").distinct()
//
//    // To DO: Number of incidents in each category
//    val sfpdCategoryCount = sfpdDS.groupBy("Category").count()
//
//    // TO DO: Print to console
//    println("Total number of incidents: %s".format(sfpdCount))
//    println("Distinct categories of incidents:" )
//    sfpdCategory.show(50)
//    println("Number of incidents in each category:")
//    sfpdCategoryCount.show(50)
//
//    sfpdCategory.toJSON.show()

//    val sfpdJSON = sfpdDS.withColumnRenamed("incidentnum","_id").toJSON
//    val sfpdDSRenamed = sfpdDS.withColumnRenamed("incidentnum","_id")
//    val sfpdJSON = sfpdDSRenamed.write.format("org.apache.spark.sql.json").save("/user/user01/scalatest8.json")

    val pocDSRenamed = pocDS.withColumnRenamed("VendorId","_id")
    val pocJSON = pocDSRenamed.write.format("org.apache.spark.sql.json").save("/user/user01/poc1.json")

//    val sfpdJSONWithId  = sfpdJSON.withColumnRenamed("incidentnum","_id")


//    println("columns:"+sfpdJSON.columns.)
//    sfpdJSONWithId.show()

    //write parquet file
//    sfpdJSON.write.save("/user/user01/scalatest.json")
//    sfpdJSON.write.json("/user/user01/scalatest3.json")


//    for(doc <- sfpdJSON){
//      println(doc)
//      Document document=  MapRDB.newDocument(doc)
//      table.insert(new MapRDB.newDocument(doc))
//      table.insert
//    }





  }
}
