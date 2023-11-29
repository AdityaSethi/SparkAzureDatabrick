package com.example.services;

import com.example.model.SparkClientData;
import com.example.model.SparkReadInput;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReadDataService {
    static Map<String, SparkSession> localSessionMap = new HashMap<>();

    public ReadDataService() {
        System.setProperty("hadoop.home.dir", "C:\\Users\\Asus\\Downloads\\hadoop-common-2.2.0-bin-master\\hadoop-common-2.2.0-bin-master");
    }

    public String createSparkSession(SparkClientData sparkClientData) {
        if (sparkClientData.getSparkSessionUUID() != null && localSessionMap.containsKey(sparkClientData.getSparkSessionUUID()))
            return "Spark session for given config is already created with UUID :" + sparkClientData.getSparkSessionUUID();
        String azureAppName = sparkClientData.getAzureAppName();
        String azureClientId = sparkClientData.getAzureClientId();
        String azureClientSecret = sparkClientData.getAzureClientSecret();
        String azureStorageAccountName = sparkClientData.getAzureStorageAccountName();

        String azureStorageAccountKey = sparkClientData.getAzureStorageAccountKey();
        String tenantId = sparkClientData.getAzureTenantId();

        // Create a Spark configuration
        SparkConf conf = new SparkConf().setAppName(azureAppName);
        SparkSession spark = SparkSession.builder().config(conf).config("spark.master", "local")
                .config("fs.azure.account.auth.type."+azureStorageAccountName+".dfs.core.windows.net", "SharedKey")
                .config("fs.azure.account.key."+azureStorageAccountName+".dfs.core.windows.net", azureStorageAccountKey)
                .getOrCreate();

        // // Set Azure Storage configuration
        spark.conf().set("fs.azure.account.auth.type", "OAuth");
        spark.conf().set("fs.azure.account.oauth.provider.type", "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider");
        spark.conf().set("fs.azure.account.auth.oauth2.client.id", azureClientId);
        spark.conf().set("fs.azure.account.auth.oauth2.client.secret", azureClientSecret);
        spark.conf().set("fs.azure.account.auth.oauth2.client.endpoint", "https://login.microsoftonline.com/" + tenantId + "/oauth2/token");
        spark.conf().set("spark.databricks.delta.preview.enabled", "true");

        System.out.println("New spark session created ******** :" + spark.sessionUUID());
        String uuid = spark.sessionUUID();
        localSessionMap.put(uuid, spark);

        return "Spark session newly created with UUID :" + uuid;
    }

    public List<String> getDataFromContainer(SparkReadInput sparkReadInput) {
        String azureStorageAccountName = sparkReadInput.getAzureStorageAccountName();

        // Define the Azure Storage container and file path
        String containerName = sparkReadInput.getAzureContainerName();
        String filePath = sparkReadInput.getAzureFilePath();
        String fileName = sparkReadInput.getAzureFileName();
        String conditionId = sparkReadInput.getAzureConditionId();

        // Read data from Azure Storage into a DataFrame
        String azurePath = "abfss://" + containerName + "@" + azureStorageAccountName + ".dfs.core.windows.net/" + filePath + "/" + fileName;

        System.out.println("**********************");
        System.out.println(azurePath);
        System.out.println("**********************");

        if (!localSessionMap.containsKey(sparkReadInput.getSparkSessionUUID()))
            return new ArrayList<String>() {
                {
                    add("Please create first valid spark session");
                }
            };

        SparkSession sparkSession = localSessionMap.get(sparkReadInput.getSparkSessionUUID());

        Dataset<Row> dataset;
        if (!conditionId.isEmpty())
            dataset = sparkSession.read().format("delta").load(azurePath).where(conditionId);
        else
            dataset = sparkSession.read().format("delta").load(azurePath);

        dataset.show();
        List<String> list = dataset.toJSON().collectAsList();

        System.out.println("data list : " + list);
        return list;
    }
}
