#!/bin/bash

####Exports
export DATA_DIR=/user/user01/poc2.json
export SPARK_BIN=/opt/mapr/spark/spark-2.1.0/bin
export MAPR_USER_HOME=/mapr/MyCluster/user/user01


#### Delete data from previous run
rm -Rf $DATA_DIR


#### Submit Spark Job to ingest and aggregate trip data JSON formatted file produced and stored on MapR-FS
$SPARK_BIN/spark-submit --class maprpoc.POCApp --master yarn $MAPR_USER_HOME/pocapp-1.0.jar


#### Store Data into MapR-DB table
mapr importJSON -idfield "_id" -src $DATA_DIR/* -dst /tmp/poctable6




