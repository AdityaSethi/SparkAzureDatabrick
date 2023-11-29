package com.example.services;

import com.example.model.SparkWriteInput;
import com.example.model.TaxiSchema;
import com.example.util.StringUtils;
import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WriteDataService {
    public WriteDataService() {
    }

    public String writeData(SparkWriteInput sparkWriteInput) {
        // Define the Azure Storage container and file path
        String containerName = sparkWriteInput.getAzureContainerName();
        String filePath = sparkWriteInput.getAzureFilePath();
        String fileName = sparkWriteInput.getAzureFileName();
        String conditionId = sparkWriteInput.getAzureConditionId();
        String azureStorageAccountName = sparkWriteInput.getAzureStorageAccountName();


        // Read data from Azure Storage into a DataFrame
        String azurePath = "abfss://" + containerName + "@" + azureStorageAccountName + ".dfs.core.windows.net/" + filePath + "/" + fileName;

        System.out.println("**********************");
        System.out.println(azurePath);
        System.out.println("**********************");

        SparkSession sparkSession = ReadDataService.localSessionMap.get(sparkWriteInput.getSparkSessionUUID());

        List<TaxiSchema> rowList = new ArrayList<>();
        rowList.add(new TaxiSchema(9999996, 3, StringUtils.dateParse("2021-12-01T00:00:00.000Z"), StringUtils.dateParse("2021-12-01T00:15:34.000Z"), 170, 140, "TAC399", "5131685", 1, 2.9, 1, 1, 15.3, 13.0, 0.5, 0.5, 1.0, 0.0, 0.3));

        Encoder<TaxiSchema> taxiSchemaEncoder = Encoders.bean(TaxiSchema.class);
        Dataset<Row> dataset = sparkSession.createDataset(rowList, taxiSchemaEncoder).toDF();
        System.out.println("---------------------------------------------------------------");
        dataset.show();
        System.out.println("---------------------------------------------------------------");

        dataset.write().format("delta").mode(SaveMode.Overwrite).save(azurePath);
        //dataset.coalesce(1).write().mode(SaveMode.Overwrite).option("header", true).parquet("C:\\Users\\Asus\\source\\repos");

        return "File write successfully";
    }
}
