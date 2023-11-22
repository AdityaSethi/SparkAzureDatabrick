package com.example.services;

import com.example.model.SparkWriteInput;
import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WriteDataService {

    public void writeData(SparkWriteInput sparkWriteInput) {
        // Define the Azure Storage container and file path
        String containerName = sparkWriteInput.getAzureContainerName();//"taxidata";
        String filePath = sparkWriteInput.getAzureFilePath();//"Output";
        String fileName = sparkWriteInput.getAzureFileName();//"YellowTaxis.delta";
        String conditionId = sparkWriteInput.getAzureConditionId();//"VendorId=4";
        String azureStorageAccountName = sparkWriteInput.getAzureStorageAccountName();//"pltaxidatalake1";


        // Read data from Azure Storage into a DataFrame
        String azurePath = "abfss://" + containerName + "@" + azureStorageAccountName + ".dfs.core.windows.net/" + filePath + "/" + fileName;

        System.out.println("**********************");
        System.out.println(azurePath);
        System.out.println("**********************");

        SparkSession sparkSession = ReadDataService.localSessionMap.get(sparkWriteInput.getSparkSessionUUID());

        List<Row> rowList = new ArrayList<>();
        rowList.add(RowFactory.create(9999996, 3, "2021-12-01T00:00:00.000Z", "2021-12-01T00:15:34.000Z", 170, 140, "TAC399", "5131685", 1, 2.9, 1, 1, 15.3, 13.0, 0.5, 0.5, 1.0, 0.0, 0.3));

        Dataset<Row> dataset = sparkSession.createDataset(rowList, getEncoder());
        dataset.show();

        dataset.coalesce(1).write().mode(SaveMode.Overwrite).option("header", true).parquet("");

        //dataset.write().format("delta").mode("overwrite").save();

    }

    private static ExpressionEncoder<Row> getEncoder() {
        StructType structType = new StructType();

        structType.add("RideId", DataTypes.IntegerType, false);
        structType.add("VendorId", DataTypes.IntegerType, false);
        structType.add("PickupTime", DataTypes.TimestampType, false);
        structType.add("DropTime", DataTypes.TimestampType, false);
        structType.add("PickupLocationId", DataTypes.IntegerType, false);
        structType.add("DropLocationId", DataTypes.IntegerType, false);
        structType.add("CabNumber", DataTypes.StringType, false);
        structType.add("DriverLicenseNumber", DataTypes.StringType, false);
        structType.add("PassengerCount", DataTypes.IntegerType, false);
        structType.add("TripDistance", DataTypes.DoubleType, false);
        structType.add("RateCodeId", DataTypes.IntegerType, false);
        structType.add("PaymentType", DataTypes.IntegerType, false);
        structType.add("TotalAmount", DataTypes.DoubleType, false);
        structType.add("FareAmount", DataTypes.DoubleType, false);
        structType.add("Extra", DataTypes.DoubleType, false);
        structType.add("MtaTax", DataTypes.DoubleType, false);
        structType.add("TipAmount", DataTypes.DoubleType, false);
        structType.add("TollsAmount", DataTypes.DoubleType, false);
        structType.add("ImprovementSurcharge", DataTypes.DoubleType, false);

        return RowEncoder.apply(structType);
    }
}
