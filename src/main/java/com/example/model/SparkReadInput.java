package com.example.model;

import lombok.Data;
import org.apache.spark.sql.SparkSession;

@Data
public class SparkReadInput {
    SparkSession sparkSession;
    String azureFilePath;
    String azureFileName;
    String azureConditionId;
    String azureStorageAccountName;
    String azureContainerName;

}
